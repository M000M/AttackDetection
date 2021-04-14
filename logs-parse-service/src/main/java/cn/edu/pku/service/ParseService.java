package cn.edu.pku.service;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queuesToDeclare = @Queue("attack logs")) // Hello模型
public class ParseService {

    @RabbitHandler
    public void parseLogs(String message) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(message);
    }
}
