package org.bank.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.bank.service.MsgSenderService;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BelongsProject: points_trading
 * @BelongsPackage: org.bank.service.impl
 * @Author: lizongle
 * @CreateTime: 2022-07-20
 * @Description:
 * @Version: 1.0
 */
@Service
@Slf4j
public class MsgSenderServiceImpl implements MsgSenderService {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendMessage(String topic, Object data) {
        rocketMQTemplate.convertAndSend(topic, data);
        log.info("发送MQ成功：message={}", JSONObject.toJSONString(data));
    }

    @Override
    public void sendMessage(String topic, String tags, Object data) {
        rocketMQTemplate.convertAndSend(String.format("%s:%s", topic, tags), data);
        log.info("发送MQ成功：message={}", JSONObject.toJSONString(data));
    }

}
