package com.yjf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:springApplication.xml")
public class ComsumerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 回退模式： 当消息发送给Exchange后，Exchange路由到Queue失败时 才会执行 ReturnCallBack
     * 步骤：
     * 1. 开启回退模式:publisher-returns="true"
     * 2. 设置ReturnCallBack
     * 3. 设置Exchange处理消息的模式：
     *      1. 如果消息没有路由到Queue，则丢弃消息（默认）
     *      2. 如果消息没有路由到Queue，返回给消息发送方ReturnCallBack
     */
    @Test
    public void testReturn() {

        // 开启消息回退
        rabbitTemplate.setMandatory(true);

        //2.设置ReturnCallBack
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * @param message   消息对象
             * @param replyCode 错误码
             * @param replyText 错误信息
             * @param exchange  交换机
             * @param routingKey 路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("return 执行了....");

                System.out.println("message: " + message);
                System.out.println("replyCode: " + replyCode);
                System.out.println("replyText: " + replyText);
                System.out.println("exchange: " + exchange);
                System.out.println("routingKey: " + routingKey);
                //处理
            }
        });

        //3. 发送消息  故意写错routing _key让他执行回调  如果信息连交换器都到不了  不会执行回调
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirmerror", "message confirm....");
    }
}