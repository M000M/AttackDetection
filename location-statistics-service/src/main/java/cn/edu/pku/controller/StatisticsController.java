package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.IpLocation;
import cn.edu.pku.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @RequestMapping(value = "/getDates", method = RequestMethod.GET)
    public CommonResult<List<String>> getDates() {
        CommonResult<List<String>> result = new CommonResult<>();
        try {
            List<String> ans = statisticsService.getDates();
            result.setData(ans);
            result.setMsg("获取一周内的日期成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("获取一周内的日期异常");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public CommonResult<List<Integer>> getData() {
        CommonResult<List<Integer>> result = new CommonResult<>();
        try {
            List<Integer> ans = statisticsService.getData();
            System.out.println(ans);
            result.setData(ans);
            result.setMsg("获取一周内的数据成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("获取一周内的数据异常");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getTop10", method = RequestMethod.GET)
    public CommonResult<List<IpLocation>> getTop10() {
        CommonResult<List<IpLocation>> result = new CommonResult<>();
        try {
            List<IpLocation> ans = statisticsService.getTop10();
            result.setData(ans);
            result.setMsg("获取最多攻击IP成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("获取最多攻击IP异常");
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
}
