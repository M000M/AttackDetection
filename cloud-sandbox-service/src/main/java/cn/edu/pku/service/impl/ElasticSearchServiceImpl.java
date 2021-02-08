package cn.edu.pku.service.impl;

import cn.edu.pku.service.ElasticSearchService;
import io.searchbox.core.Index;
import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    public void getAllLogs () {
        Index index = new Index.Builder();
        elasticsearchClient.execute();
    }
}
