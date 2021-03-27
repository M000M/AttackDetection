package cn.edu.pku.service;

import cn.edu.pku.entities.LogInfo;
import cn.edu.pku.entities.NginxLogInfo;

import java.util.List;

public interface ElasticSearchService {

    List<NginxLogInfo> getAll();

    long getTotal();

    List<Object> getLogByPage(int start, int size);
}
