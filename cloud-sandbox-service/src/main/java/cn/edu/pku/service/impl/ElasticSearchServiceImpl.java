package cn.edu.pku.service.impl;

import cn.edu.pku.entities.LogInfo;
import cn.edu.pku.entities.NginxLogInfo;
import cn.edu.pku.service.ElasticSearchService;
import cn.edu.pku.utils.JsonUtils;
import cn.edu.pku.utils.TimeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public List<NginxLogInfo> getAll() {
        List<NginxLogInfo> res = new ArrayList<>();

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("logstash-nginx-access");
        searchRequest.types("_doc");

        //MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("uri", "/addUser");

        SortBuilder sortBuilder = new FieldSortBuilder("accessTime")
                .order(SortOrder.DESC);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        QueryBuilder totalFilter = QueryBuilders.boolQuery();
                //.filter(matchQuery);
        int size = 5000;
        int from = 0;
        long total = 0;

        do {
            try {
                sourceBuilder.query(totalFilter).from(from).size(size);
                //sourceBuilder.sort(sortBuilder).query(totalFilter).from(from).size(size);
                sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
                searchRequest.source(sourceBuilder);

//                SearchResponse response = restHighLevelClient.search(searchRequest);
                SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

                SearchHit[] hits = response.getHits().getHits();
                for (SearchHit hit: hits) {
                    //System.out.println(hit.getSourceAsString());
                    NginxLogInfo nginxLogInfo = (NginxLogInfo) JsonUtils.stringToObject(hit.getSourceAsString(), NginxLogInfo.class);
                    res.add(nginxLogInfo);
                }

                if (from > 100) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (from < total);
//        Collections.sort(res, new Comparator<NginxLogInfo>() {
//            @Override
//            public int compare(NginxLogInfo o1, NginxLogInfo o2) {
//                int temp = o1.getAccessTime().compareTo(o2.getAccessTime());
//                if (temp > 0) return -1;
//                else if (temp == 0) return 0;
//                else return 1;
//            }
//        });
        return res;
    }

    @Override
    public long getTotal() {
        CountRequest countRequest = new CountRequest();
        countRequest.indices("filebeat-log");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        countRequest.source(searchSourceBuilder);

        long res = 0;
        try {
            CountResponse response = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            res = response.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Object> getLogByPage(int start, int size) {
        List<Object> result = new ArrayList<>();

        SearchRequest searchRequest = new SearchRequest();

        searchRequest.indices("filebeat-log");

        searchRequest.types("_doc");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        QueryBuilder totalFilter = QueryBuilders.boolQuery();

        try {
            sourceBuilder.query(totalFilter).from(start).size(size);
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(sourceBuilder);
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] hits = response.getHits().getHits();
            for (int i = hits.length - 1; i >= 0; i--) {
                SearchHit hit = hits[i];
                String log = hit.getSourceAsString();
                System.out.println(log);
                JSONObject obj = JSON.parseObject(log);
                String message = null;
                JSONObject obj1 = null;
                message = obj.getString("message");
                System.out.println(message);
                if (message.startsWith("{\"log\"")) {
                    obj1 = JSON.parseObject(message);
                    String time = null;
                    if ((time = obj1.getString("time")) != null) {
                        obj1.replace("time", parseTZTime(time));
                    }
                } else {
                    obj1 = new JSONObject();
                    obj1.put("log", message);
                    obj1.put("time", TimeUtils.formatTime(new Date()));
                }
                result.add(obj1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String parseTZTime(String time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = null;
        try {
            Date date = sdf1.parse(time);
            date.setTime(date.getTime() + 28800000);
            str = sdf2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
