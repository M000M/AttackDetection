package cn.edu.pku.dao;

import cn.edu.pku.entities.StatisticsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface StatisticsMapper {

    int addRecord(StatisticsInfo statisticsInfo);

    StatisticsInfo getRecordByIpAndDate(@Param("ip") String ip, @Param("date") String date);

    int updateRecord(StatisticsInfo statisticsInfo);
}
