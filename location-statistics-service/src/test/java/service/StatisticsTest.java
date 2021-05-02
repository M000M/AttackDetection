package service;

import cn.edu.pku.LocationStatisticsServiceMain;
import cn.edu.pku.dao.StatisticsMapper;
import cn.edu.pku.entities.StatisticsInfo;
import cn.edu.pku.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest(classes = LocationStatisticsServiceMain.class)
@RunWith(SpringRunner.class)
public class StatisticsTest {

    @Resource
    private StatisticsMapper statisticsMapper;

    @Test
    public void test() {
        String ip = "59.151.43.78";
        int count = 7;
        String date = DateUtils.fromDateToString(new Date(1619323262000L));
        System.out.println(date);

        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.setIp(ip);
        statisticsInfo.setCount(count);
        statisticsInfo.setDate(date);

        System.out.println(statisticsInfo);

        int res = statisticsMapper.addRecord(statisticsInfo);
        if (res > 0) {
            System.out.println("insert yes");
        } else {
            System.out.println("insert failure");
        }
    }

    @Test
    public void testQuery() {
        String ip = "216.24.193.116";
        //String date = DateUtils.fromDateToString(new Date());
        String date = "2021-04-29";
        StatisticsInfo statisticsInfo = statisticsMapper.getRecordByIpAndDate(ip, date);
        System.out.println(statisticsInfo);
    }

    @Test
    public void testUpdate() {
        String ip = "216.24.193.116";
        //String date = DateUtils.fromDateToString(new Date());
        String date = "2021-04-29";
        StatisticsInfo statisticsInfo = statisticsMapper.getRecordByIpAndDate(ip, date);
        statisticsInfo.setCount(statisticsInfo.getCount() + 1);
        int res = statisticsMapper.updateRecord(statisticsInfo);
        if (res > 0) {
            System.out.println("update success.");
        } else {
            System.out.println("update failure.");
        }
    }
}
