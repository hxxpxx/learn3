package org.bank.rocketmq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.bank.constants.Constant;
import org.bank.service.UserService;
import org.bank.vo.req.UserUpdateIntegralReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: points_trading
 * @BelongsPackage: org.bank.service
 * @Author: lizongle
 * @CreateTime: 2022-07-20  16:40
 * @Description: 订单超时未支付消息消费
 * @Version: 1.0
 */
@Service
@RocketMQMessageListener(consumerGroup = Constant.SYSTEM_SERVER_MQ_GROUP,topic = Constant.ORDER_MQ_TOPIC_KEY,selectorExpression = Constant.ORDER_PAY_ADD_TAG_KEY)
@Slf4j
public class OrderPayAddMQConsumerService implements RocketMQListener<String> {
    @Autowired
    UserService userService;

    @Override
    public void onMessage(String s) {
        log.info("开始订单积分退还接口{}",s);
        try{
            UserUpdateIntegralReqVO userUpdateIntegralReqVO= JSONObject.parseObject(s,UserUpdateIntegralReqVO.class);
            userService.updateIntegral(userUpdateIntegralReqVO);
        }catch (Exception e){
            log.info("订单积分退还失败:{}",e.getMessage());
            throw e;
        }
    }
}
