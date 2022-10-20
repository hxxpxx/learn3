package org.bank.rockermq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.bank.constants.Constant;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.message.OrderDelayMessageVO;
import org.bank.service.OrderService;
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
@RocketMQMessageListener(consumerGroup = Constant.ORDER_SERVER_MQ_GROUP,topic = Constant.ORDER_MQ_TOPIC_KEY,selectorExpression = Constant.ORDER_DELAY_PAY_TAG_KEY)
@Slf4j
public class OrderDelayMQConsumerService implements RocketMQListener<String> {
    @Autowired
    OrderService orderService;

    @Override
    public void onMessage(String s) {
        log.info("开始消费延时消息{}",s);
        try{
            OrderDelayMessageVO orderDelayMessageVO= JSONObject.parseObject(s,OrderDelayMessageVO.class);
            orderService.cancleOrder(orderDelayMessageVO.getOrderNo());
        }catch (Exception e){
            log.info("订单超时未支付取消订单失败:{}",e.getMessage());
            if(e instanceof BusinessException){
                if(BaseResponseCode.CANCLE_ORDER_STATUS_ERROR.getCode()!=((BusinessException) e).getMessageCode()){
                      throw e;
                }else{
                    log.info("订单超时未支付取消订单失败:{}",e.getMessage());
                }
            }else{
                throw e;
            }
        }
    }
}
