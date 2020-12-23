package com.yjf;

import org.junit.Test;
import org.junit.runner.RunWith;
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
    public void testSend() {
       while (true){

       }
    }
}