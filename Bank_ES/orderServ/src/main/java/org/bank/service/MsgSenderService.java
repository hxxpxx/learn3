package org.bank.service;

/**
 * @BelongsProject: points_trading
 * @BelongsPackage: org.bank.service
 * @Author: lizongle
 * @CreateTime: 2022-07-20
 * @Description:
 * @Version: 1.0
 */
public interface MsgSenderService {
    /**
     * 发送消息
     *
     * @param data  消息信息
     * @param topic 主题
     */
    void sendMessage(String topic, Object data);

    /**
     * 发送消息
     *
     * @param data  消息信息
     * @param topic 主题
     * @param tags  主题的标签
     */
    void sendMessage(String topic, String tags, Object data);


}

