package cn.edu.pku.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PositionMapper {

    int lastPosition();

    int updatePosition(@Param("new_position") long newPosition);
}
