package cn.edu.pku.service;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
//@RabbitListener(queuesToDeclare = @Queue("ip address"))
public class ParseIpService {

    @RabbitListener(queuesToDeclare = @Queue("ip address"))
    public void parseIp(String message) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(message);
    }
}
