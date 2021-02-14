package cn.edu.pku.service;

import cn.edu.pku.entities.NginxLogInfo;

import java.util.List;

public interface ElasticSearchService {

    List<NginxLogInfo> getAll();
}
