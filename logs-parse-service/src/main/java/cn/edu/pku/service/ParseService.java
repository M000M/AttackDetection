package cn.edu.pku.service;

import cn.edu.pku.entities.RegularExpression;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RabbitListener(queuesToDeclare = @Queue("attack logs")) // Hello模型
public class ParseService {

    private static List<RegularExpression> expressions = null;

    static {

    }

    @RabbitHandler
    public void parseLogs(String message) {

    }
}
