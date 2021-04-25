package cn.edu.pku.service;

import cn.edu.pku.entities.IpLocation;

import java.util.List;

public interface LocationService {

    List<IpLocation> getChinaIpLocationList();

    boolean addChinaIpRecord(String province);

    int getCountByProvince(String province);

    boolean updateChinaIpRecord(String province, int count);

    /*World IP Location*/
    List<IpLocation> getWorldIpLocation();

    boolean addWorldIpRecord(String country);

    int getCountByCountry(String country);

    boolean updateWorldIpRecord(String country, int count);
}
