import cn.edu.pku.CloudSandboxServiceMain;
import cn.edu.pku.entities.LogInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.DocValueFormat;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = CloudSandboxServiceMain.class)
@RunWith(SpringRunner.class)
public class ESTest {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test() {
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.indices("filebeat-log");

        searchRequest.types("_doc");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        QueryBuilder totalFilter = QueryBuilders.boolQuery();

        int size = 100;
        int from = 100000;
        try {
            sourceBuilder.query(totalFilter).from(from).size(size);
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(sourceBuilder);

            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                String log = hit.getSourceAsString();
                JSONObject obj = JSON.parseObject(log);
                String message = obj.getString("message");
                System.out.println(message);

//                if (!log.contains("log")) {
//                    continue;
//                }
//                LogInfo logInfo = new LogInfo();
//                JSONObject obj = JSON.parseObject(log);
//                String msg = obj.getString("message");
//                //System.out.println(msg);
//                obj = JSON.parseObject(msg);
//                String messageLog = obj.getString("log");
//                String time = obj.getString("time");
//
//                int pos1 = messageLog.indexOf("HoneyPotSSHTransport");
//                int pos2 = messageLog.indexOf("]");
//                if (pos1 == -1 || pos2 == -1 || pos2 <= pos1) {
//                    continue;
//                }
//                String subStr = messageLog.substring(pos1, pos2);
//                String ip = subStr.split(",")[2];
//                logInfo.setIp(ip);
//
//                int pos3 = messageLog.indexOf("Command found");
//                if (pos3 == -1) {
//                    continue;
//                }
//                String cmd = messageLog.substring(pos3).split(":")[1].replace("\n", "");
//                logInfo.setCmd(cmd);
//
//                String[] times = time.split("T");
//                String[] part1 = times[0].split("-");
//                int year = Integer.parseInt(part1[0]);
//                int month = Integer.parseInt(part1[1]);
//                int day = Integer.parseInt(part1[2]);
//                String[] part2 = times[1].split("\\.")[0].split(":");
//                int hour = Integer.parseInt(part2[0]);
//                int minute = Integer.parseInt(part2[1]);
//                int seconds = Integer.parseInt(part2[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        CountRequest countRequest = new CountRequest();
        countRequest.indices("filebeat-log");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        countRequest.source(searchSourceBuilder);

        try {
            CountResponse response = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            long res = response.getCount();
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        String time = "2021-03-26T11:16:42+0000";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf1.parse(time);
            date.setTime(date.getTime() + 28800000);
            String str = sdf2.format(date);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
















