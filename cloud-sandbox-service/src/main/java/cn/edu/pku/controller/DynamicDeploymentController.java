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
}
