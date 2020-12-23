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
     * 发送测试死信消息：
     * 1. 过期时间
     * 2. 长度限制
     * 3. 消息拒收
     */
    @Test
    public void testDlx() {
        //1. 测试过期时间，死信消息
//        rabbitTemplate.convertAndSend("test_exchange_dlx","test_dlx","dlx.....");

        //2. 测试长度限制后，消息死信
   /* for (int i = 0; i < 20; i++) {
        rabbitTemplate.convertAndSend("test_exchange_dlx","test_dlx","dlx....."+i);
    }*/

        //3. 测试消息拒收
        rabbitTemplate.convertAndSend("test_exchange_dlx", "test_dlx", "dlx...");

    }
}