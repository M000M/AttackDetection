package cn.edu.pku.service;

import cn.edu.pku.entities.IpLocation;
import cn.edu.pku.entities.StatisticsInfo;

import java.util.List;

public interface StatisticsService {

    List<String> getDates();

    List<Integer> getData();

    List<IpLocation> getTop10();

    boolean addRecord(String ip);
}
