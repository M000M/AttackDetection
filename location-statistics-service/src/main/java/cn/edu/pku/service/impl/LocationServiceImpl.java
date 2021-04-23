package cn.edu.pku.service.impl;

import cn.edu.pku.dao.LocationMapper;
import cn.edu.pku.entities.ChinaIpLocation;
import cn.edu.pku.service.LocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Resource
    private LocationMapper locationMapper;

    @Override
    public List<ChinaIpLocation> getChinaIPLocationList() {
        return locationMapper.getChinaIPLocationList();
    }
}
