package org.bank.rocketmq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.bank.constants.Constant;
import org.bank.service.AccountService;
import org.bank.vo.req.AccountTransactionReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: points_trading
 * @BelongsPackage: org.bank.service
 * @Author: lizongle
 * @Version: 1.0
 */
@Service
@RocketMQMessageListener(consumerGroup = Constant.SYSTEM_SERVER_MQ_GROUP,topic = Constant.ORDER_MQ_TOPIC_KEY,selectorExpression = Constant.ORDER_PAY_ADD_TAG_KEY)
@Slf4j
public class OrderPayAddMQConsumerService implements RocketMQListener<String> {

    @Autowired
    AccountService accountService;

    @Override
    public void onMessage(String s) {
        log.info("开始订单账户退款接口{}",s);
        try{
            AccountTransactionReqVO accountTransactionReqVO= JSONObject.parseObject(s,AccountTransactionReqVO.class);
            accountService.deductionByAccountNo(accountTransactionReqVO);
        }catch (Exception e){
            log.info("订单账户退款失败:{}",e.getMessage());
            throw e;
        }
    }
}
