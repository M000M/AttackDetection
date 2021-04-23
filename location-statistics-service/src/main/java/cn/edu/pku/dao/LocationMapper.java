package cn.edu.pku.dao;

import cn.edu.pku.entities.ChinaIpLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface LocationMapper {

    List<ChinaIpLocation> getChinaIPLocationList();

    int addIpRecord(@Param("ip") String ip, @Param("province") String province,
                    @Param("count") int count, @Param("time") Date time);
}
