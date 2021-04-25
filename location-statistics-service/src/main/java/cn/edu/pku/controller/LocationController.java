package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.IpLocation;
import cn.edu.pku.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/location")
public class LocationController {

    @Resource
    private LocationService locationService;

    @RequestMapping(value = "/china", method = RequestMethod.GET)
    public CommonResult<List<IpLocation>> getChinaIPLocationList() {
        CommonResult<List<IpLocation>> result = new CommonResult<>();
        try {
            List<IpLocation> res = locationService.getChinaIpLocationList();
            result.setData(res);
            result.setMsg("获取中国IP位置列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("获取中国IP位置列表异常");
        }
        return result;
    }

    @RequestMapping(value = "/world", method = RequestMethod.GET)
    public CommonResult<List<IpLocation>> getWorldIPLocationList() {
        CommonResult<List<IpLocation>> result = new CommonResult<>();
        try {
            List<IpLocation> res = locationService.getWorldIpLocation();
            result.setData(res);
            result.setMsg("获取世界IP位置列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("获取世界IP位置列表异常");
        }
        return result;
    }
}