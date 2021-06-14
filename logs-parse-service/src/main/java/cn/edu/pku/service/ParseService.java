package cn.edu.pku.service;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.RegularExpression;
import cn.edu.pku.utils.RedisUtils;
import cn.edu.pku.utils.SHA256Utils;
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
    private VerificationLogsService verificationLogsService;

    @Resource
    private DAGFeignService dagFeignService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisUtils redisUtils;

    private List<RegularExpression> expressions = null;

    /***
     * 判断是哪种蜜罐类型的蜜罐受到攻击的正则表达式
     */
    private static final String cowriePattern = ".*CowrieSSHFactory.*";

    private static final String conpotPattern = ".*HTTP/1.1.*";

    private static final String adbhoneyPattern = ".*adbhoney.*";

    private static final String citrixhoneypotPattern = ".*GET Header:.*";

    private static final String honeytrapPattern = ".*honeytrap/server.*";

    // Redis 中几种类型蜜罐的键
    private static final String cowrieKey = "cowrie";

    private static final String conpotKey = "conpot";

    private static final String adbhoneyKey = "adbhoney";

    private static final String citrixhoneypotKey = "citrixhoneypot";

    private static final String honeytrapKey = "honeytrap";

    // Redis 过期时间，设置为 180 秒 (3分钟)
    private static final int EXPIRE_TIME = 60;

    private static final String reg = "[^a-zA-Z ]";

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
        Pattern p = Pattern.compile("\r|\n");
        Matcher m = p.matcher(message);
        message = m.replaceAll("");

        String logHash = SHA256Utils.sha256Code(message);
        CommonResult<String> res = dagFeignService.addLogHash(logHash);
        String hashAddress = res.getData();
        verificationLogsService.addLog(message, hashAddress);
        refreshRedis(message);
        helper(message);
    }

//    @RabbitListener(queuesToDeclare = @Queue("attack logs"))
//    public void parseLogs2(String message) throws JSONException {
//        helper(message);
//    }
//
//    @RabbitListener(queuesToDeclare = @Queue("attack logs"))
//    public void parseLogs3(String message) throws JSONException {
//        helper(message);
//    }

    private void helper(String message) throws JSONException {
        if (expressions == null) {
            expressions = expressionService.getAllExpression();
        }
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
                    String str = pattern.replaceAll(reg, "");
                    obj.put(field, str);
                }
            }
        }
        if (obj.length() < 2) {
            return;
        }
        if (obj.has("ip") && obj.getString("ip") != null) {
            String ip = obj.getString("ip");
            rabbitTemplate.convertAndSend("address", ip);
        }
        String str = obj.toString();
        System.out.println(str);
        resultService.addResult(str);
        System.out.println("-----------------------------------------------------------------------");
    }

    /***
     * 根据日志中的关键信息，判断是哪种类型蜜罐的攻击，
     * 更新该蜜罐最近一分钟所遭受的攻击次数
     * @param message
     */
    private void refreshRedis(String message) {
        if (Pattern.matches(cowriePattern, message)) { // cowrie型蜜罐
            redisUtils.incr(cowrieKey, 1);
            redisUtils.expire(cowrieKey, EXPIRE_TIME);
        } else if (Pattern.matches(conpotPattern, message)) { // conpot型蜜罐
            redisUtils.incr(conpotKey, 1);
            redisUtils.expire(conpotKey, EXPIRE_TIME);
        } else if (Pattern.matches(adbhoneyPattern, message)) { // adbhoney型蜜罐
            redisUtils.incr(adbhoneyKey, 1);
            redisUtils.expire(adbhoneyKey, EXPIRE_TIME);
        } else if (Pattern.matches(citrixhoneypotPattern, message)) { // citrixhoneypot型蜜罐
            redisUtils.incr(citrixhoneypotKey, 1);
            redisUtils.expire(citrixhoneypotKey, EXPIRE_TIME);
        } else if (Pattern.matches(honeytrapPattern, message)) {
            redisUtils.incr(honeytrapKey, 1);
            redisUtils.expire(honeytrapKey, EXPIRE_TIME);
        }
    }
}
