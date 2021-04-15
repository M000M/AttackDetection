package cn.edu.pku.service;

import cn.edu.pku.entities.LogInfo;
import cn.edu.pku.entities.NginxLogInfo;

import java.util.List;

public interface ElasticsearchService {

    long getTotal();

    List<Object> getLogByPage(int start, int size);

    List<Object> getRealTimeLog(int start, int size);
}
