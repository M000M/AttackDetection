package cn.edu.pku.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VerificationLogsMapper {

    int addLog(@Param("message") String message, @Param("hash") String hash);

    String getLogById(@Param("id") long id);

    String getLogHashById(@Param("id") long id);
}
