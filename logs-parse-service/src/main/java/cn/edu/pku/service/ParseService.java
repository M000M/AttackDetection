package cn.edu.pku.service;

import cn.edu.pku.entities.RegularExpression;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
//@RabbitListener(queuesToDeclare = @Queue("attack logs")) // Hello模型
public class ParseService {

    @Resource
    private ExpressionService expressionService;

    @Resource
    private ResultService resultService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    private List<RegularExpression> expressions = null;

//    @RabbitHandler
//    public void parseLogs(String message) {
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(message);
//    }

    @RabbitListener(queuesToDeclare = @Queue("attack logs"))
    public void parseLogs1(String message) throws JSONException {
        helper(message);
    }

    @RabbitListener(queuesToDeclare = @Queue("attack logs"))
    public void parseLogs2(String message) throws JSONException {
        helper(message);
    }

    @RabbitListener(queuesToDeclare = @Queue("attack logs"))
    public void parseLogs3(String message) throws JSONException {
        helper(message);
    }

    private void helper(String message) throws JSONException {
        if (expressions == null) {
            expressions = expressionService.getAllExpression();
        }
        //System.out.println(message);
        //DetectionResult detectionResult = new DetectionResult();
        JSONObject obj = new JSONObject();
        String pattern = null;
        String field = null;
        int type = 0;
        Pattern r = null;
        Matcher m = null;
        for (RegularExpression expression : expressions) {
            pattern = expression.getExpression();
            field = expression.getField();
            type = expression.getType();
            if (type == 1) {
                r = Pattern.compile(pattern);
                m = r.matcher(message);
                if (m.find()) {
                    obj.put(field, m.group());
                }
            }
            if (type == 2) {
                boolean isMatch = Pattern.matches(pattern, message);
                if (isMatch) {
                    obj.put(field, pattern);
                }
            }
        }
        if (obj.length() < 3) {
            return;
        }
        if (obj.getString("ip") != null) {
            String ip = obj.getString("ip");
            rabbitTemplate.convertAndSend("address", ip);
        }
        String str = obj.toString();
        System.out.println(str);
        resultService.addResult(str);
        System.out.println("-----------------------------------------------------------------------");
    }
}
