package cn.edu.pku.service;

import java.util.List;

public interface ElasticsearchService {

    long getTotal();

    List<Object> getLogByPage(int start, int size);
}
