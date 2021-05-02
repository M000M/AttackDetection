package cn.edu.pku.service.impl;

import cn.edu.pku.dao.StatisticsMapper;
import cn.edu.pku.entities.IpLocation;
import cn.edu.pku.entities.StatisticsInfo;
import cn.edu.pku.service.StatisticsService;
import cn.edu.pku.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final long oneDayMillis = 86400000L;

    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    public List<String> getDates() {
        List<String> result = new ArrayList<>();
        Date today = new Date();
        long now = today.getTime();
        for (int i = 6; i >= 1; i--) {
            Date date = new Date(now - i * oneDayMillis);
            result.add(DateUtils.fromDateToString(date));
        }
        result.add(DateUtils.fromDateToString(today));
        return result;
    }

    @Override
    public List<Integer> getData() {
        List<Integer> result = new ArrayList<>();
        List<String> dates = getDates();
        for (String date: dates) {
            int count = statisticsMapper.getCountByDate(date);
            result.add(count);
        }
        return result;
    }

    @Override
    public List<IpLocation> getTop10() {
        return statisticsMapper.getTop10();
    }

    @Override
    public boolean addRecord(String ip) {
        int res = 0;
        String date = DateUtils.fromDateToString(new Date());
        StatisticsInfo statisticsInfo = statisticsMapper.getRecordByIpAndDate(ip, date);
        if (statisticsInfo == null) {
            StatisticsInfo record = new StatisticsInfo();
            record.setIp(ip);
            record.setCount(1);
            record.setDate(date);
            res = statisticsMapper.addRecord(record);
        } else {
            statisticsInfo.setCount(statisticsInfo.getCount() + 1);
            res = statisticsMapper.updateRecord(statisticsInfo);
        }
        return res > 0;
    }
}
