package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.service.DynamicDeploymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/dynamic")
public class DynamicDeploymentController {

    @Resource
    private DynamicDeploymentService dynamicDeploymentService;

    @RequestMapping(value = "/getCountByImageName", method = RequestMethod.GET)
    public CommonResult<Integer> getRunningContainerCountByImageName(@RequestParam("imageName") String imageName) {
        CommonResult<Integer> result = new CommonResult<>();
        try {
            int res = dynamicDeploymentService.getRunningContainerCountByImageName(imageName);
            result.setData(res);
            result.setMsg("根据镜像名获取运行容器数量成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("根据镜像名获取运行容器数量异常");
        }
        return result;
    }

    @RequestMapping(value = "/getRecentAttackCount", method = RequestMethod.GET)
    public CommonResult<Integer> getRecentAttackCount(@RequestParam("key") String key) {
        CommonResult<Integer> result = new CommonResult<>();
        try {
            int res = dynamicDeploymentService.getRecentAttackCount(key);
            result.setData(res);
            result.setMsg("获取最近攻击次数成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("获取最近攻击次数异常");
        }
        return result;
    }

    @RequestMapping(value = "/setAttackCountRateByType", method = RequestMethod.GET)
    public CommonResult<Boolean> setAttackCountRateByType(@RequestParam("type") String type, @RequestParam("maxRate") Integer maxRate) {
        CommonResult<Boolean> result = new CommonResult<>();
        try {
            boolean res = dynamicDeploymentService.setAttackCountRateByType(type, maxRate);
            result.setData(res);
            result.setMsg(type + " 类型的攻击/数量比值设置为了 " + maxRate);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setData(false);
            result.setMsg("设置新的攻击/数量比值异常");
        }
        return result;
    }
}
