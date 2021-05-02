package cn.edu.pku.dao;

import cn.edu.pku.entities.IpLocation;
import cn.edu.pku.entities.StatisticsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface StatisticsMapper {

    int addRecord(StatisticsInfo statisticsInfo);

    StatisticsInfo getRecordByIpAndDate(@Param("ip") String ip, @Param("date") String date);

    int updateRecord(StatisticsInfo statisticsInfo);

    int getCountByDate(@Param("date") String date);

    List<IpLocation> getTop10();
}
