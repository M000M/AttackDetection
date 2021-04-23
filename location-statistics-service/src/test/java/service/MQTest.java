package service;

import cn.edu.pku.LocationStatisticsServiceMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = LocationStatisticsServiceMain.class)
@RunWith(SpringRunner.class)
public class MQTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend("ip address", "", "49.232.78.91");
        }
    }

//    @Test
//    @RabbitListener(queuesToDeclare = @Queue("ips"))
//    public void consumer(String message) {
//        System.out.println(message);
//    }
}
