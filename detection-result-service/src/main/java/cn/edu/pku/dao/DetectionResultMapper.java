package cn.edu.pku.dao;

import cn.edu.pku.entities.DetectionResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DetectionResultMapper {

    int getTotal();

    List<DetectionResult> getResultByPage(@Param("start") int start, @Param("size") int size);
}
