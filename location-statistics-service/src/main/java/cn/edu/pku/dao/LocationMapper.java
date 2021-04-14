package cn.edu.pku.dao;

import cn.edu.pku.entities.ChinaIpLocation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {
    List<ChinaIpLocation> getChinaIPLocationList();
}
