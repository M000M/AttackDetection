package cn.edu.pku.service.impl;

import cn.edu.pku.dao.LocationMapper;
import cn.edu.pku.entities.IpLocation;
import cn.edu.pku.service.LocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Resource
    private LocationMapper locationMapper;

    @Override
    public List<IpLocation> getChinaIpLocationList() {
        return locationMapper.getChinaIpLocationList();
    }

    @Override
    public boolean addChinaIpRecord(String province) {
        int count = locationMapper.getCountByProvince(province);
        int res = 0;
        if (count == 0) {
            res = locationMapper.addChinaIpRecord(province, 1);
        } else {
            res = locationMapper.updateChinaIpRecord(province, count + 1);
        }
        return res > 0;
    }

    @Override
    public int getCountByProvince(String province) {
        return locationMapper.getCountByProvince(province);
    }

    @Override
    public boolean updateChinaIpRecord(String province, int count) {
        int res = locationMapper.updateChinaIpRecord(province, count);
        return res > 0;
    }

    @Override
    public List<IpLocation> getWorldIpLocation() {
        return locationMapper.getWorldIpLocationList();
    }

    @Override
    public boolean addWorldIpRecord(String country) {
        int count = locationMapper.getCountByCountry(country);
        int res = 0;
        if (count == 0) {
            res = locationMapper.addWorldIpRecord(country, 1);
        } else {
            res = locationMapper.updateWorldIpRecord(country, count + 1);
        }
        return res > 0;
    }

    @Override
    public int getCountByCountry(String country) {
        return locationMapper.getCountByCountry(country);
    }

    @Override
    public boolean updateWorldIpRecord(String country, int count) {
        int res = locationMapper.updateWorldIpRecord(country, count);
        return res > 0;
    }
}
