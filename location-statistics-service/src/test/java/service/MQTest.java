package service;

import cn.edu.pku.LocationStatisticsServiceMain;
import cn.edu.pku.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootTest(classes = LocationStatisticsServiceMain.class)
@RunWith(SpringRunner.class)
public class MQTest {
    private final OkHttpClient client = new OkHttpClient();
    private static final String worldPath = "https://freegeoip.app/json/";

    // Parse China IP
    private static final String path = "http://cz88.rtbasia.com/search";
    private static final String appcode = "e3ae57b8c1e7462a9111759944c5834a";
    HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(path)).newBuilder();

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test1() {
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend("address", "46.101.122.214");
        }
    }

    public void test2() throws IOException {
        //String ipAddress = "210.83.84.54";
        String ipAddress = "49.232.78.91";
        urlBuilder.addQueryParameter("ip", ipAddress);
        Request request = new Request.Builder()
                .addHeader("Authorization", "APPCODE " + appcode)
                .url(urlBuilder.build())
                .get()
                .build();
        Response response = client.newCall(request).execute();
        try {
            JSONObject obj = JSON.parseObject(Objects.requireNonNull(response.body()).string());
            String str = obj.getString("data");
            JSONObject data = JSON.parseObject(str);
            String location = data.getString("province");
            String province = location.split("省")[0];
            province = province.replace("市", "");
            if (province.startsWith("广西")) {
                province = "广西";
            }
            if (province.startsWith("内蒙古")) {
                province = "内蒙古";
            }
            if (province.startsWith("新疆")) {
                province = "新疆";
            }
            if (province.startsWith("西藏")) {
                province = "西藏";
            }
            if (province.startsWith("宁夏")) {
                province = "宁夏";
            }
            if (province.startsWith("香港")) {
                province = "香港";
            }
            if (province.startsWith("澳门")) {
                province = "澳门";
            }
            if (province.startsWith("台湾")) {
                province = "台湾";
            }
            System.out.println(province);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
