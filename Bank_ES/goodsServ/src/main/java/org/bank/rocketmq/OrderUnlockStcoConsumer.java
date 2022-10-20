package org.bank.rocketmq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.bank.constants.Constant;
import org.bank.message.OrderPayMessageVO;
import org.bank.service.GoodsService;
import org.bank.vo.req.GoodsDeductStockReqVO;
import org.bank.vo.req.GoodsStockReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: points_trading
 * @BelongsPackage: org.bank.rocketmq
 * @Author: lizongle
 * @CreateTime: 2022-07-22  10:44
 * @Description: 预解锁库存消息
 * @Version: 1.0
 */
@Service
@RocketMQMessageListener(consumerGroup = Constant.GOODS_SERVER_MQ_GROUP+Constant.ORDER_CANCEL_TAG_KEY,topic = Constant.ORDER_MQ_TOPIC_KEY,selectorExpression = Constant.ORDER_CANCEL_TAG_KEY)
@Slf4j
public class OrderUnlockStcoConsumer implements RocketMQListener<String> {
    @Autowired
    GoodsService goodsService;
    @Override
    public void onMessage(String s) {
        log.info("准备消费预解锁商品库存消息{}",s);
        try{
            GoodsStockReqVO goodsStockReqVO= JSONObject.parseObject(s,GoodsStockReqVO.class);
            goodsService.unLockStock(goodsStockReqVO.getStackNum(),goodsStockReqVO.getGoodsId());
        }catch (Exception e){
            log.info("消费预解锁商品库存消息失败:{}",e.getMessage());
            throw e;
        }
    }
}
