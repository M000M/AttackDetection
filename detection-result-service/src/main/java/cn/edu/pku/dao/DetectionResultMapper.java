package cn.edu.pku.dao;

import cn.edu.pku.entities.DetectionResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DetectionResultMapper {

    int getTotal();

    List<DetectionResult> getAll();
}
