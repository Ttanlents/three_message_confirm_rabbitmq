package com.yjf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:springApplication.xml")
public class ProducerTest {
    @Autowired
    RabbitTemplate rabbitTemplate;   //让队列的消息过期

    /**
     * 延迟队列
     * TTL+死信队列
     */
    @Test
    public void testDelay() {
        //1. 测试过期时间，死信消息
        MessageProperties prop = new MessageProperties();
        // 5s不消费消息将成为死信
        prop.setExpiration("5000");

        Message message = new Message("delay=ttl+dl".getBytes(), prop);

        rabbitTemplate.convertAndSend("test_queue_dlx2", message);
    }
}