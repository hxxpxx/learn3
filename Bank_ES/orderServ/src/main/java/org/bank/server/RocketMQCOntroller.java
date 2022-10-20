package org.bank.server;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 普通信息的三种方式：同步、异步、单向
 * @author qzz
 */
@RequestMapping("/tre")
@RestController
public class RocketMQCOntroller {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    /**
     * 发送普通消息
     * convertAndSend(String destination, Object payload) 发送字符串比较方便
     */
    @GetMapping("/send")
    public void send(){
        rocketMQTemplate.convertAndSend("test-topic","test-message");
    }
    /**
     * 发送同步消息
     */
    @GetMapping("/testSyncSend")
    public void testSyncSend(){
        //参数一：topic   如果想添加tag,可以使用"topic:tag"的写法
        //参数二：消息内容
        SendResult sendResult = rocketMQTemplate.syncSend("test-topic","同步消息测试");
        System.out.println(sendResult);
    }
    /**
     * 发送异步消息
     */
    @GetMapping("/testASyncSend")
    public void testASyncSend(){
        //参数一：topic   如果想添加tag,可以使用"topic:tag"的写法
        //参数二：消息内容
        //参数三：回调
        rocketMQTemplate.asyncSend("test-topic", "异步消息测试", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }
            @Override
            public void onException(Throwable throwable) {
                System.out.println("消息发送异常");
                throwable.printStackTrace();
            }
        });
    }
    /**
     * 发送单向消息
     */
    @GetMapping("/testOneWay")
    public void testOneWay(){
        //参数一：topic   如果想添加tag,可以使用"topic:tag"的写法
        //参数二：消息内容
        rocketMQTemplate.sendOneWay("test-topic","单向消息测试");
    }
}