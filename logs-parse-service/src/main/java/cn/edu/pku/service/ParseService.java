package cn.edu.pku.service;

import cn.edu.pku.entities.DetectionResult;
import cn.edu.pku.entities.RegularExpression;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RabbitListener(queuesToDeclare = @Queue("attack logs")) // Hello模型
public class ParseService {

    @Resource
    private ExpressionService expressionService;

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

    @RabbitHandler
    public void parseLogs(String message) throws JSONException {
        if (expressions == null) {
            expressions = expressionService.getAllExpression();
        }
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
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            System.out.println(obj);
            System.out.println("-----------------------------------------");
        }
    }
}
