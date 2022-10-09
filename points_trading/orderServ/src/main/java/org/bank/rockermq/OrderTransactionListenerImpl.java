package org.bank.rockermq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.aspectj.weaver.ast.Or;
import org.bank.client.UserClient;
import org.bank.constants.Constant;
import org.bank.entity.Order;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.message.OrderDelayMessageVO;
import org.bank.message.OrderPayMessageVO;
import org.bank.service.MsgSenderService;
import org.bank.service.OrderService;
import org.bank.utils.DataResult;
import org.bank.vo.req.OrderUpdateReqVO;
import org.bank.vo.req.UserUpdateIntegralReqVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @BelongsProject: points_trading
 * @BelongsPackage: org.bank.rockermq
 * @Author: lizongle
 * @CreateTime: 2022-07-21  15:29
 * @Description:
 * @Version: 1.0
 */
@Service
@RocketMQTransactionListener
@Slf4j
public class OrderTransactionListenerImpl implements RocketMQLocalTransactionListener {
    @Autowired
    UserClient userClient;

    @Autowired
    OrderService orderService;

    @Autowired
    MsgSenderService msgSenderService;
    /**
     * @description:执行本地事务
     * @author: lizongle
     * @param: [message, o]
     * @return: org.apache.rocketmq.spring.core.RocketMQLocalTransactionState
     **/
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("准备消费订单支付事务消息");
        RocketMQLocalTransactionState resultState = RocketMQLocalTransactionState.COMMIT;
        boolean isUpdateIntegral=false;
        Order order = null;
        OrderPayMessageVO vo=(OrderPayMessageVO) o;
        if(o==null){
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        try {
            log.info("执行订单事务{}",vo.toString());
            //查询订单
            order=orderService.detailInfo(vo.getOrderNo());
            if(order==null|| StringUtils.isBlank(order.getUserId())){
                log.info("订单支付获取订单信息错误{}","订单不存在");
                throw new BusinessException(BaseResponseCode.PAY_ORDERNO_ORDERNO_ERRPR);
            }
            //调用积分扣除
            UserUpdateIntegralReqVO userUpdateIntegralReqVO=new UserUpdateIntegralReqVO();
            userUpdateIntegralReqVO.setId(order.getUserId());
            userUpdateIntegralReqVO.setIntegral(order.getTotalAmout());
            userUpdateIntegralReqVO.setType(false);
            DataResult dataResult=userClient.updateIntegral(userUpdateIntegralReqVO);
            if(dataResult.getCode()==0){
                isUpdateIntegral=true;
                Order update=new Order();
                update.setPaytime(new Date());
                update.setStatus("paid");
                update.setUpdatetime(new Date());
                update.setOrderNo(order.getOrderNo());
                OrderUpdateReqVO updateReqVO=new OrderUpdateReqVO();
                BeanUtils.copyProperties(update,updateReqVO);
                orderService.updateOrder(updateReqVO);
            }else{
                log.info("订单【"+order.getOrderNo()+"】扣除积分信息失败，原因为{}",dataResult.getMsg());
                throw new BusinessException(BaseResponseCode.PAY_ORDERNO_ERRPR);
            }
        }catch (Exception e){
            log.info("准备消费订单支付事务消息异常，异常原因为{}",e.getMessage());
            if(isUpdateIntegral){
                //调用退款接口
                UserUpdateIntegralReqVO userUpdateIntegralReqVO=new UserUpdateIntegralReqVO();
                userUpdateIntegralReqVO.setId(order.getUserId());
                userUpdateIntegralReqVO.setIntegral(order.getTotalAmout());
                userUpdateIntegralReqVO.setType(true);
                msgSenderService.sendMessage(Constant.ORDER_MQ_TOPIC_KEY,Constant.ORDER_PAY_ADD_TAG_KEY,JSONObject.toJSONString(userUpdateIntegralReqVO));
            }
            if(order!=null){
                Order update=new Order();
                OrderUpdateReqVO updateReqVO=new OrderUpdateReqVO();
                updateReqVO.setStatus("fail");
                updateReqVO.setOrderNo(order.getOrderNo());
                update.setUpdatetime(new Date());
                orderService.updateOrder(updateReqVO);
                //预解锁商品库存
                OrderDelayMessageVO orderDelayMessageVO=new OrderDelayMessageVO();
                orderDelayMessageVO.setOrderNo(order.getOrderNo());
                msgSenderService.sendMessage(Constant.ORDER_MQ_TOPIC_KEY,Constant.ORDER_DELAY_PAY_TAG_KEY, JSONObject.toJSONString(orderDelayMessageVO));
            }
            resultState=RocketMQLocalTransactionState.ROLLBACK;
        }
        return resultState;
    }

    /**
     * @description:检查本地事务
     * @author: lizongle
     * @param: [message]
     * @return: org.apache.rocketmq.spring.core.RocketMQLocalTransactionState
     **/
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        log.info("checkLocalTransaction");
        RocketMQLocalTransactionState resultState = RocketMQLocalTransactionState.UNKNOWN;
        try {
            OrderPayMessageVO vo= JSONObject.parseObject(message.toString(),OrderPayMessageVO.class);
            log.info("检查订单事务{}",vo.toString());
            //查询订单
            Order order=orderService.detailInfo(vo.getOrderNo());
            if(order==null|| StringUtils.isBlank(order.getUserId())){
                log.info("订单支付获取订单信息错误{}","订单不存在");
                resultState=RocketMQLocalTransactionState.ROLLBACK;
                return resultState;
            }
            if("paid".equals(order.getStatus())){
                resultState=RocketMQLocalTransactionState.COMMIT;
            }
            if("fail".equals(order.getStatus())||"cancel".equals(order.getStatus())){
                resultState=RocketMQLocalTransactionState.ROLLBACK;
            }
        }catch (Exception e){
            resultState=RocketMQLocalTransactionState.ROLLBACK;
        }
        return resultState;
    }



}
