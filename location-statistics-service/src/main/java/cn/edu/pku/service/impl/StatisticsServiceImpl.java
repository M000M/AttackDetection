package cn.edu.pku.service.impl;

import cn.edu.pku.dao.StatisticsMapper;
import cn.edu.pku.entities.StatisticsInfo;
import cn.edu.pku.service.StatisticsService;
import cn.edu.pku.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;

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
