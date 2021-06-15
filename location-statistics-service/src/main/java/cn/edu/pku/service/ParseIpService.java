package cn.edu.pku.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

@Service
public class ParseIpService {
    private final OkHttpClient client = new OkHttpClient();
    private static final String worldPath = "https://freegeoip.app/json/";

    // Parse China IP
    private static final String path = "http://cz88.rtbasia.com/search";
    //private static final String appcode = "e3ae57b8c1e7462a9111759944c5834a";
    // 叶德鹏的AppCode
    //private static final String appcode = "a8a0afff65f942279e09cdd97ff75d15";
    // 李昭的AppCode
    private static final String appcode = "f15a8e42ff5143be9cf20356865b2407";
    HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(path)).newBuilder();

    @Resource
    private LocationService locationService;

    @Resource
    private StatisticsService statisticsService;

    @RabbitListener(queuesToDeclare = @Queue("address"))
    public void parseIp(String message) throws IOException {
        statisticsService.addRecord(message);
        String query = worldPath + message;
        Request request = new Request.Builder()
                .url(query)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        JSONObject obj = JSON.parseObject(Objects.requireNonNull(response.body()).string());
        String countryName = obj.getString("country_name");
        if (countryName != null) {
            System.out.println("country: " + countryName);
            if (countryName.equals("Hong Kong")
            || countryName.equals("Taiwan")
            || countryName.equals("Macao")) {
                countryName = "China";
            }
            if (countryName.equals("South Korea")) {
                countryName = "Korea";
            }
            boolean res = locationService.addWorldIpRecord(countryName);
            if (res && (countryName.equals("China"))) {
                parseChinaIp(message);
            }
        }
    }

    private void parseChinaIp(String chinaIp) throws IOException {
        System.out.println("chinaIp: " + chinaIp);
        urlBuilder.removeAllQueryParameters("ip");
        urlBuilder.addQueryParameter("ip", chinaIp);
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
            System.out.println("省份: " + province);
            boolean res = locationService.addChinaIpRecord(province);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
