package cn.edu.pku.service;

import cn.edu.pku.entities.LogInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogsService {

    int getTotal();

    List<LogInfo> getLogsByPage(int start, int size);
}
