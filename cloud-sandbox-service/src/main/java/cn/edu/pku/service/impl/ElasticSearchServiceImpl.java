package cn.edu.pku.service.impl;

import cn.edu.pku.entities.NginxLogInfo;
import cn.edu.pku.service.ElasticSearchService;
import cn.edu.pku.utils.JsonUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
        int size = 100;
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
}
