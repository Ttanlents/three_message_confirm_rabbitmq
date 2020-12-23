package com.yjf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:springApplication.xml")
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * rabbitmq事务处理模式
     *      事务模式不能与confirms、returns并存,需要去spring配置文件中删除publisher-confirms="true"和publisher-returns="true"配置
     *
     */
    @Test
    @Transactional
    @Rollback(false)        // 是否自动回滚(false:代表不是自动回滚),如果关闭了出现异常也不会回滚(测试异常的时候改为true)
    public void testTx() {

        // 开启rabbitmq对事物的支持
        rabbitTemplate.setChannelTransacted(true);

        // 开启消息回退
        rabbitTemplate.setMandatory(true);
        // 3. 发送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message confirm1...");

        int i = 1 / 0;          // 模拟异常

        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message confirm2...");
    }
}