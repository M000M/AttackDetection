package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.DetectionResult;
import cn.edu.pku.service.DetectionResultService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/result")
public class DetectionResultController {
    @Resource
    private DetectionResultService detectionResultService;

    @RequestMapping(value = "/getTotal", method = RequestMethod.GET)
    public CommonResult<Integer> getTotal() {
        CommonResult<Integer> result = new CommonResult<>();
        try {
            int ans = detectionResultService.getTotal();
            result.setData(ans);
            result.setMsg("查询数量成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("查询数量异常");
        }
        return result;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<Object>> list() {
        CommonResult<List<Object>> result = new CommonResult<>();
        try {
            List<Object> ans = detectionResultService.getAll();
            result.setData(ans);
            result.setMsg("查询全部检测结果成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("查询全部检测结果异常");
        }
        return result;
    }
}
