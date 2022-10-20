package org.bank.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.bank.client.AddressClient;
import org.bank.client.GoodsClient;
import org.bank.constants.Constant;
import org.bank.dao.OrderEsMapper;
import org.bank.entity.*;
import org.bank.esEntity.OrderES;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.lock.redisson.RedissonLock;
import org.bank.mapper.OrderMapper;
import org.bank.message.OrderDelayMessageVO;
import org.bank.message.OrderPayMessageVO;
import org.bank.service.*;
import org.bank.utils.CommonUtils;
import org.bank.utils.DataResult;
import org.bank.utils.PageUtils;
import org.bank.vo.req.*;
import org.bank.vo.resp.PageVO;
import org.bank.vo.resp.PermissionRespNode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderEsMapper orderEsMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    RedissonLock redissonLock;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    AddressClient addressClient;

    @Autowired
    GoodsClient goodsClient;

    @Autowired
    OrderAddressService orderAddressService;

    @Autowired
    OrderSnapshotService orderSnapshotService;

    @Autowired
    MsgSenderService msgSenderService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private long redisTimeOut = 1000 * 60 * 60;

    /**
     * 签发订单号给页面
     *
     * @return
     */
    @Override
    public String issueOrderNo() {
        //获取分布式锁
        try {
            if (redissonLock.lock("getOrderNoLock", 10)) {
                String orderNo = "order_" + UUID.randomUUID().toString();
                return orderNo;
            } else {
                throw new BusinessException(BaseResponseCode.GET_ORDERNO_AWAIT);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //释放锁
            redissonLock.release("getOrderNoLock");
        }

    }

    @Override
    public boolean cancelOrderNo(String orderNo) {
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    /**
     * 创建订单
     * @param vo
     * @return
     */
    @Override
    public Order addOrder(OrderAddReqVO vo, String userId) {
        String orderNo = vo.getOrderNo();
        //获取锁，消费订单号
        Order addOrder = null;
        boolean isUnLockStock = false;
        boolean isInsertES = false;
        //获取分布式锁
        try {
            if (redissonLock.lock(Constant.ORDERNO_LOCK_KEY + orderNo, 10)) {
                //如果没有商品ID  直接返回
                if (org.apache.commons.lang3.StringUtils.isBlank(vo.getGoodsId())) {
                    throw new BusinessException(BaseResponseCode.GET_ORDER_GOODS_ERROR);
                }
                if (vo.getNum() == null || vo.getNum() <= 0) {
                    throw new BusinessException(BaseResponseCode.GET_ORDER_NUM_ERROR);
                }
                addOrder = new Order();
                BeanUtils.copyProperties(vo, addOrder);
                //组装数据
                addOrder = makerderData(addOrder);
                addOrder.setUserId(userId);
                //数据校验
                checkOrderData(addOrder);
                int insertSize = 0;
                insertSize = orderMapper.insert(addOrder);
                if (insertSize <= 0) {
                    throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
                }
                //入es库
                OrderES orderES = new OrderES();
                BeanUtils.copyProperties(addOrder, orderES);
                orderES.setAddressInfo(addOrder.getOrderAddress().getAddress());
                orderES.setContacts(addOrder.getOrderAddress().getContacts());
                orderES.setPhone(addOrder.getOrderAddress().getPhone());
                orderES.setTimestamp(addOrder.getCreatetime().getTime());
                orderEsMapper.save(orderES);
                isInsertES = true;
                //交易快照
                orderSnapshotService.addOrderSnapshot(addOrder.getOrderSnapshot());
                //订单收获地址
                orderAddressService.addOrderAddress(addOrder.getOrderAddress());
                //锁定库存
                GoodsStockReqVO goodsStockReqVO = new GoodsStockReqVO();
                goodsStockReqVO.setGoodsId(addOrder.getGoodsId());
                goodsStockReqVO.setStackNum(addOrder.getNum());
                DataResult<Boolean> goodsStockLockResult = goodsClient.lockStock(goodsStockReqVO);
                if (goodsStockLockResult.getCode() != 0) {
                    log.info("订单创建失败，失败原因为{}", goodsStockLockResult.getMsg());
                    throw new BusinessException(BaseResponseCode.GOODS_STOCK_IS_OUT);
                } else {
                    isUnLockStock = true;
                }
                //发送延时消息
                //参数一：topic   如果想添加tag,可以使用"topic:tag"的写法
                //参数二：Message<?>
                //参数三：消息发送超时时间
                //参数四：delayLevel 延时level  messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
                OrderDelayMessageVO orderDelayMessageVO = new OrderDelayMessageVO();
                orderDelayMessageVO.setOrderNo(vo.getOrderNo());
                SendResult sentResult = rocketMQTemplate.syncSend(String.format("%s:%s", Constant.ORDER_MQ_TOPIC_KEY, Constant.ORDER_DELAY_PAY_TAG_KEY), MessageBuilder.withPayload(JSONObject.toJSONString(orderDelayMessageVO)).build(), 3000, 16);
                return addOrder;
            } else {
                throw new BusinessException(BaseResponseCode.SYSTEM_BUSY);
            }
        } catch (Exception e) {
            log.error("创建订单异常，异常原因为{}", e.getMessage());
            if (isUnLockStock) {
                GoodsStockReqVO goodsStockReqVO = new GoodsStockReqVO();
                goodsStockReqVO.setStackNum(addOrder.getNum());
                goodsStockReqVO.setGoodsId(addOrder.getGoodsId());
                goodsClient.unLockStock(goodsStockReqVO);
//              msgSenderService.sendMessage(Constant.ORDER_MQ_TOPIC_KEY,Constant.ORDER_CANCEL_TAG_KEY, JSONObject.toJSONString(goodsStockReqVO));
            }
            if (isInsertES) {
                //删除es记录
                orderEsMapper.deleteById(orderNo);
            }
            if (e instanceof BusinessException) {
                throw e;
            } else {
                throw new BusinessException(BaseResponseCode.GET_ORDER_CREATE_ERROR);
            }

        } finally {
            redissonLock.release(Constant.ORDERNO_LOCK_KEY + orderNo);
        }

    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void cancleOrder(String orderNo) {
        //校验订单号
        if (StringUtils.isNotBlank(orderNo)) {
            Order orderInfo = detailInfo(orderNo);
            OrderES orderES = getOrderES(orderNo);
            //只有未支付的单子可以取消订单操作
            if (orderInfo != null && StringUtils.isNotBlank(orderInfo.getGoodsId()) && "unpaid".equals(orderInfo.getStatus())) {
                //取消订单
                Order update = new Order();
                update.setOrderNo(orderNo);
                update.setStatus("cancel");
                update.setRemark("订单已取消");
                update.setUpdatetime(new Date());
                int updateSize = orderMapper.updateByPrimaryKeySelective(update);
                if (updateSize > 0) {
                    //更新es
                    orderES.setRemark("订单已取消");
                    orderES.setStatus("cancel");
                    orderEsMapper.save(orderES);
                    //解锁商品库存
                    GoodsStockReqVO goodsStockReqVO = new GoodsStockReqVO();
                    goodsStockReqVO.setStackNum(orderInfo.getNum());
                    goodsStockReqVO.setGoodsId(orderInfo.getGoodsId());
                    msgSenderService.sendMessage(Constant.ORDER_MQ_TOPIC_KEY, Constant.ORDER_CANCEL_TAG_KEY, JSONObject.toJSONString(goodsStockReqVO));
                } else {
                    throw new BusinessException(BaseResponseCode.CANCLE_ORDER_ERROR);
                }

            } else {
                throw new BusinessException(BaseResponseCode.CANCLE_ORDER_STATUS_ERROR);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void cancleOrder(String orderNo, String userId) {
        //校验订单号
        if (StringUtils.isNotBlank(orderNo)) {
            Order orderInfo = detailInfo(orderNo);
            OrderES orderES = getOrderES(orderNo);
            if (StringUtils.isBlank(userId) || !userId.equals(orderInfo.getUserId())) {
                throw new BusinessException(BaseResponseCode.OP_ORDER_USER_ERROR);
            }
            //只有未支付的单子可以取消订单操作
            if (orderInfo != null && StringUtils.isNotBlank(orderInfo.getGoodsId()) && "unpaid".equals(orderInfo.getStatus())) {
                //取消订单
                Order update = new Order();
                update.setOrderNo(orderNo);
                update.setStatus("cancel");
                update.setRemark("订单已取消");
                update.setUpdatetime(new Date());
                int updateSize = orderMapper.updateByPrimaryKeySelective(update);
                if (updateSize > 0) {
                    orderES.setStatus("cancel");
                    orderES.setRemark("订单已取消");
                    orderEsMapper.save(orderES);
                    //解锁商品库存
                    GoodsStockReqVO goodsStockReqVO = new GoodsStockReqVO();
                    goodsStockReqVO.setStackNum(orderInfo.getNum());
                    goodsStockReqVO.setGoodsId(orderInfo.getGoodsId());
                    msgSenderService.sendMessage(Constant.ORDER_MQ_TOPIC_KEY, Constant.ORDER_CANCEL_TAG_KEY, JSONObject.toJSONString(goodsStockReqVO));
                } else {
                    throw new BusinessException(BaseResponseCode.CANCLE_ORDER_ERROR);
                }
            } else {
                throw new BusinessException(BaseResponseCode.CANCLE_ORDER_STATUS_ERROR);
            }
        }
    }

    @Override
    public void payOrder(OrderPayReqVO orderPayReqVO, String userId) {
        //校验订单号
        try {
            Order orderInfo = detailInfo(orderPayReqVO.getOrderNo());
            if (orderInfo == null || StringUtils.isBlank(orderInfo.getGoodsId())) {
                throw new BusinessException(BaseResponseCode.PAY_ORDERNO_ORDERNO_ERRPR);
            }
            if (orderInfo.getUserId() == null || !orderInfo.getUserId().equals(userId)) {
                //非本人结束操作
                throw new BusinessException(BaseResponseCode.OP_ORDER_USER_ERROR);
            }
            //判断订单状态
            if (!"unpaid".equals(orderInfo.getStatus())&&!"fail".equals(orderInfo.getStatus())) {
                throw new BusinessException(BaseResponseCode.PAY_ORDER_STATUS_ERRPR);
            }
            //订单支付
            //2发送事务消息  扣除积分与库存
            OrderPayMessageVO orderPayMessageVO = new OrderPayMessageVO();
            BeanUtils.copyProperties(orderInfo, orderPayMessageVO);
            orderPayMessageVO.setAccountNo(orderPayReqVO.getAccountNo());
            orderPayMessageVO.setUserId(userId);
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction
                    (String.format("%s:%s", Constant.ORDER_MQ_TOPIC_KEY, Constant.ORDER_PAY_TAG_KEY), MessageBuilder.withPayload(orderPayMessageVO).build(), orderPayMessageVO);
            log.info("事务消息发送结果为{}",transactionSendResult);
        } catch (Exception e) {
            throw new BusinessException(BaseResponseCode.PAY_ORDERNO_ERRPR);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void updateOrder(OrderUpdateReqVO vo) throws Exception {
        String orderNo = vo.getOrderNo();
        if (StringUtils.isNotBlank(orderNo)) {
            Order orderInfo = detailInfo(orderNo);
            OrderES orderES = getOrderES(orderNo);
            if (orderInfo == null || StringUtils.isBlank(orderInfo.getGoodsId())) {
                return;
            }
            Order update = new Order();
            BeanUtils.copyProperties(vo, update);
            update.setUpdatetime(new Date());
            int updateSize = orderMapper.updateByPrimaryKeySelective(update);
            if (updateSize == 0) {
                throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
            }
            CommonUtils.copyProperties(update, orderES);
            orderEsMapper.save(orderES);

        }
    }

    /**
     * @param order
     * @return
     */
    private boolean checkOrderData(Order order) {
        //校验订单号
        Order checkOrder = detailInfo(order.getOrderNo());
        if (checkOrder != null) {
            throw new BusinessException(BaseResponseCode.GET_ORDERNO_ISEXIT);
        }
        //校验地址
        if (order.getOrderAddress() == null || StringUtils.isEmpty(order.getOrderAddress().getAddress())) {
            throw new BusinessException(BaseResponseCode.GET_ORDER_ADDRESS_ERROR);
        }
        //校验商品
        if (order.getGoods() == null || StringUtils.isEmpty(order.getTitle())) {
            throw new BusinessException(BaseResponseCode.GET_ORDER_GOODS_ERROR);
        }
        return true;
    }

    //组装数据
    private Order makerderData(Order order) {
        Date createTime = new Date();
        order.setCreatetime(createTime);
        order.setStatus("unpaid");
        //收获地址信息
        Integer addressId = order.getAddressId();
        if (addressId != null && addressId != 0) {
            DataResult<Address> addressDataResult = addressClient.detailInfo(addressId);
            if (addressDataResult.getCode() == 0 && addressDataResult.getData() != null) {
                Address address = addressDataResult.getData();
                OrderAddress orderAddress = new OrderAddress();
                orderAddress.setAddress(address.getAddress());
                orderAddress.setContacts(address.getContacts());
                orderAddress.setPhone(address.getPhone());
                orderAddress.setOrderNo(order.getOrderNo());
                orderAddress.setCreatetime(createTime);
                order.setOrderAddress(orderAddress);
            }
        }
        //商品信息
        String goodsId = order.getGoodsId();
        if (!StringUtils.isEmpty(goodsId)) {
            DataResult<Goods> goodsDataResult = goodsClient.detailInfo(goodsId);
            if (goodsDataResult.getCode() == 0 && goodsDataResult.getData() != null) {
                Goods goods = goodsDataResult.getData();
                order.setGoods(goods);
                order.setTitle(goods.getName());
                Double price = goods.getPrice() == null ? 0.00 : goods.getPrice();
                Double totalAmout = null;
                BigDecimal priceDc = new BigDecimal(price.toString());
                BigDecimal numDc = new BigDecimal((order.getNum() == null ? new Integer(0) : order.getNum()).toString());
                totalAmout = priceDc.multiply(numDc).doubleValue();
                order.setTotalAmout(totalAmout);
                //交易快照信息
                OrderSnapshot orderSnapshot = new OrderSnapshot();
                orderSnapshot.setOrderNo(order.getOrderNo());
                orderSnapshot.setCreatetime(createTime);
                orderSnapshot.setGoodsId(goodsId);
                orderSnapshot.setTitle(goods.getName());
                orderSnapshot.setDescribe(goods.getDescribe());
                orderSnapshot.setPrice(goods.getPrice());
                order.setOrderSnapshot(orderSnapshot);
            }
        }
        return order;
    }


    @Override
    public Order detailInfo(String orderNo, String userId) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        if (StringUtils.isBlank(userId) || !userId.equals(order.getUserId())) {
            throw new BusinessException(BaseResponseCode.OP_ORDER_USER_ERROR);
        }
        return order;
    }

    @Override
    public Order detailInfo(String orderNo) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        return order;
    }

    private OrderES getOrderES(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }
        OrderES orderES = null;
        Optional<OrderES> optional = orderEsMapper.findById(orderNo);
        if (optional != null && optional.get() != null) {
            orderES = optional.get();
        } else {
            Order order = detailInfo(orderNo);
            if (order != null) {
                BeanUtils.copyProperties(order, orderES);
                OrderAddress orderAddress = getAddressForOrderNo(orderNo);
                orderES.setAddressInfo(orderAddress.getAddress());
                orderES.setContacts(orderAddress.getContacts());
                orderES.setPhone(orderAddress.getPhone());
            }
        }
        return orderES;
    }

    @Override
    public PageVO<OrderES> pageInfo(OrderPageReqVO vo) {
        List<OrderES> orderList = new ArrayList<>();
        PageVO<OrderES> result = new PageVO<>();
        Page<OrderES> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        try {
            SearchRequest searchRequest = new SearchRequest("orderbean");
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            //模糊查询
            if (StringUtils.isNotBlank(vo.getOrderNo())) {
                boolQueryBuilder.filter(QueryBuilders.queryStringQuery("*" + vo.getOrderNo() + "*").field("orderNo.keyword"));
            }
            //精确查询
            if (StringUtils.isNotBlank(vo.getUserId())) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("userId", vo.getUserId()));
            }
            //精确查询
            if (StringUtils.isNotBlank(vo.getStatus())) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("status", vo.getStatus()));
            }
            //全文搜索
            if (StringUtils.isNotBlank(vo.getKey())) {
                boolQueryBuilder.filter(QueryBuilders.multiMatchQuery(vo.getKey(), "title", "contacts", "addressInfo","remark"));
            }
            //排序
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.
                    sort(SortBuilders.fieldSort("timestamp").unmappedType("keyword").order(SortOrder.DESC)).query(boolQueryBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();

            for (SearchHit hit : hits) {
                Map<String, Object> map = hit.getSourceAsMap();
                //日期类型需要重新设置 防止json格式化失败
                map.remove("createtime");
                OrderES orderES = JSONObject.parseObject(JSONObject.toJSONString(map), OrderES.class);
                orderES.setCreatetime(new Date(orderES.getTimestamp()));
                orderList.add(orderES);
            }
            result.setTotalRows((long) response.getHits().getTotalHits().value);
            result.setTotalPages(orderList.size() % page.getPageSize() > 0 ? orderList.size() % page.getPageSize() + 1 : orderList.size() % page.getPageSize());
            result.setPageNum(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setCurPageSize(page.getPageSize());
            result.setList(orderList);
        } catch (IOException e) {
            log.info("ES查询异常，{}", e);
            throw new BusinessException(BaseResponseCode.ES_OPTION_ERROR);
        }
        return result;
    }

    @Override
    public OrderAddress getAddressForOrderNo(String orderNo) {
        return orderAddressService.detailInfo(orderNo);
    }

    @Override
    public OrderSnapshot getOrderSnapshotForOrderNo(String orderNo) {
        return orderSnapshotService.detailInfo(orderNo);
    }

}
