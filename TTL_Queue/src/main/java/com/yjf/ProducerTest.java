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
    @Test
    public  void  demo(){
        rabbitTemplate.convertAndSend("test_queue_ttl","ttl....");
    }

    /**
     * TTL:过期时间
     * 2. 消息单独过期
     * <p>
     * 如果设置了消息的过期时间，也设置了队列的过期时间，它以时间短的为准。
     * 队列过期后，会将队列所有消息全部移除。
     */
    @Test
    public void testTtl2() {

        // 消息配置类
        MessageProperties messageProperties = new MessageProperties();

        //设置消息的过期时间: 2秒   可以提前比队列设置的过期时间快
        messageProperties.setExpiration("2000");

        // 封装一条消息
        Message message = new Message("ttl....".getBytes(), messageProperties);

        rabbitTemplate.convertAndSend("test_queue_ttl", message);
    }

    @Test
    public void testTtl3() {

        // 消息配置类
        MessageProperties messageProperties = new MessageProperties();
        //设置消息的过期时间，5秒
        messageProperties.setExpiration("2000");

        // 封装一条消息
        Message message = new Message("ttl..".getBytes(), messageProperties);
        for (int i = 1; i <= 4; i++) {
            if (i == 2) {
                // i等于2的时候发送一条带有过期时间的消息
                rabbitTemplate.convertAndSend("test_queue_ttl",message);
            } else {
                rabbitTemplate.convertAndSend("test_queue_ttl", "no ttl...");
            }
        }
    }
}