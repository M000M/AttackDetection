package cn.edu.pku.dao;

import cn.edu.pku.entities.IpLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LocationMapper {

    List<IpLocation> getChinaIpLocationList();

    int addChinaIpRecord(@Param("province") String province,
                         @Param("count") int count);

    int getCountByProvince(@Param("province") String province);

    int updateChinaIpRecord(@Param("province") String province,
                            @Param("count") int count);

    /*World IP Location*/
    List<IpLocation> getWorldIpLocationList();

    int addWorldIpRecord(@Param("country") String country,
                         @Param("count") int count);

    int getCountByCountry(@Param("country") String country);

    int updateWorldIpRecord(@Param("country") String country,
                            @Param("count") int count);

}
