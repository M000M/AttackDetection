package cn.edu.pku.controller;

import cn.edu.pku.entities.ChinaIpLocation;
import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResult<List<ChinaIpLocation>> getChinaIPLocationList() {
        CommonResult<List<ChinaIpLocation>> result = new CommonResult<>();
        try {
            List<ChinaIpLocation> res = locationService.getChinaIPLocationList();
            result.setData(res);
            result.setMsg("获取中国IP位置列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("获取中国IP位置列表异常");
        }
        return result;
    }
}
