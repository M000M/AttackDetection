package cn.edu.pku.config;

import org.apache.hc.client5.http.config.RequestConfig.Builder;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchClientConfig {

    @Bean
    @Qualifier("restHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("81.70.240.72", 9200, "http"))
                //RestClient.builder(new HttpHost("49.232.78.91", 9200, "http"))
                        .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                            // 该方法接收一个RequestConfig.Builder对象，对该对象进行修改后然后返回。
                            @Override
                            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                                return builder.setConnectionRequestTimeout(5000 * 1000)  // 连接超时（默认为1秒）
                                        .setSocketTimeout(6000 * 1000); // 套接字超时（默认为30秒）//更改客户端的超时限制默认30秒现在改为100*1000分钟
                            }
                            // 调整最大重试超时时间（默认为30秒）.setMaxRetryTimeoutMillis(60000);
                        })
                        //.setMaxRetryTimeoutMillis(300 * 10000)
        );
        return restHighLevelClient;
    }
}
