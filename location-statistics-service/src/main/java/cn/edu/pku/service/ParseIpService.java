package cn.edu.pku.service;

import cn.edu.pku.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
//@RabbitListener(queuesToDeclare = @Queue("ip address"))
public class ParseIpService {
    private static String host = "http://cz88.rtbasia.com";
    //private static String host = "http://ip-api.com";
    private static String path = "/search";
    //private static String path = "/json/";
    private static String method = "GET";
    private static String appcode = "e3ae57b8c1e7462a9111759944c5834a";
    private static Map<String, String> headers = new HashMap<String, String>();
    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105

    private static Map<String, String> querys = new HashMap<String, String>();

    private static int num = 0;
    public ParseIpService() {
        headers.put("Authorization", "APPCODE " + appcode);
    }

    @RabbitListener(queuesToDeclare = @Queue("address"))
    public void parseIp(String message) {
        System.out.println("第 " + num++ + " 次查询");
        System.out.println(message);
        try {
            querys.put("ip", message);
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
//            HttpEntity httpEntity = response.getEntity();
//            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private static final String charSet = "UTF-8";
//    private static final String path = "http://ip-api.com/json/";
//    private static int num = 0;
//    private URL url = null;
//    HttpURLConnection conn = null;
//    InputStream is = null;
//    BufferedReader br = null;



//    @RabbitListener(queuesToDeclare = @Queue("address"))
//    public void parseIp(String message) throws IOException {
//        System.out.println("第 " + num++ + " 个请求");
//        if (num == 20) {
//            sleep1s();
//            num = 0;
//        }
//        url = new URL(path + message);
//        conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("accept", "*/*");
//        conn.setRequestProperty("connection", "Keep-Alive");
//        conn.connect();
//        is = conn.getInputStream();
//        br = new BufferedReader(new InputStreamReader(is));
//        String str = null;
//        while ((str = br.readLine()) != null) {
//            str = new String(str.getBytes());
//            System.out.println(str);
//            JSONObject obj = JSON.parseObject(str);
//            System.out.println(obj.getString("country"));
//        }
//        is.close();
//        conn.disconnect();
//        System.out.println("请求完成");
//    }
//
//    private void sleep1s() {
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    HttpClient httpClient = new HttpClient();
//
//    @RabbitListener(queuesToDeclare = @Queue("address"))
//    public void parseIp(String message) {
//        System.out.println("第 " + num++ + " 个请求");
//        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//        String url = path + message;
//        GetMethod getMethod = new GetMethod(url);
//        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
//        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//        String response = null;
//        try {
//            int statusCode = httpClient.executeMethod(getMethod);
//            if (statusCode != HttpStatus.SC_OK) {
//                System.out.println("请求出错" + getMethod.getStatusLine());
//            }
////            Header[] headers = getMethod.getResponseHeaders();
////            for (Header h: headers) {
////                System.out.println(h.getName() + " ----------- " + h.getValue());
////            }
//
//            byte[] responseBody = getMethod.getResponseBody();
//            response = new String(responseBody, charSet);
//            System.out.println(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            getMethod.releaseConnection();
//        }
//    }
}
