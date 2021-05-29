package cn.edu.pku.dao;

import cn.edu.pku.entities.LogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogsMapper {

    int getTotal();

    List<LogInfo> getLogsByPage(@Param("start") int start, @Param("size") int size);
}
