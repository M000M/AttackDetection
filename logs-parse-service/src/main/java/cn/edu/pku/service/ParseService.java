package cn.edu.pku.service;

import cn.edu.pku.entities.RegularExpression;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "attack logs", type = "fanout")
            )
    })
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
        }
        if (obj.length() < 3) {
            return;
        }
        if (obj.getString("ip") == null) {
            return;
        }
        if (obj.has("command")) {
            String command = obj.getString("command");
            obj.remove("command");
            command = command.replace("Command found: ", "");
            obj.put("command", command);
        }
        String str = obj.toString();
        resultService.addResult(str);
        System.out.println(str);
        System.out.println("-----------------------------------------");
    }
}
