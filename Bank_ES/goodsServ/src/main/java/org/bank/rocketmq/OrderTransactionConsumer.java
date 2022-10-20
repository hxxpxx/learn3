package org.bank.rocketmq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.autoconfigure.ListenerContainerConfiguration;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.bank.constants.Constant;
import org.bank.message.OrderPayMessageVO;
import org.bank.service.GoodsService;
import org.bank.vo.req.GoodsDeductStockReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: points_trading
 * @BelongsPackage: org.bank.rockermq
 * @Author: lizongle
 * @CreateTime: 2022-07-21  16:44
 * @Description:
 * @Version: 1.0
 */
@Service
@RocketMQMessageListener(consumerGroup = Constant.GOODS_SERVER_MQ_GROUP+Constant.ORDER_PAY_TAG_KEY,topic = Constant.ORDER_MQ_TOPIC_KEY,selectorExpression = Constant.ORDER_PAY_TAG_KEY)
@Slf4j
public class OrderTransactionConsumer implements RocketMQListener<String> {

    @Autowired
    GoodsService goodsService;

    @Override
    public void onMessage(String s) {
        log.info("准备扣除商品库存消息{}",s);
        try{
            OrderPayMessageVO orderPayMessageVO= JSONObject.parseObject(s,OrderPayMessageVO.class);
            GoodsDeductStockReqVO reqvo=new GoodsDeductStockReqVO();
            reqvo.setId(orderPayMessageVO.getGoodsId());
            reqvo.setStock(orderPayMessageVO.getNum());
            goodsService.deductStock(reqvo);
        }catch (Exception e){
            log.info("准备扣除商品库存消息失败:{}",e.getMessage());
            throw e;
        }
    }
}
