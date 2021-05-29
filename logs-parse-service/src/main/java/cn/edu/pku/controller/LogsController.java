package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.LogInfo;
import cn.edu.pku.service.LogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping(value = "/logs")
public class LogsController {

    @Resource
    private LogsService logsService;

    @RequestMapping(value = "/getTotal", method = RequestMethod.GET)
    public CommonResult<Integer> getTotal() {
        CommonResult<Integer> result = new CommonResult<>();
        try {
            int res = logsService.getTotal();
            result.setData(res);
            result.setMsg("获取日志总数成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("获取日志总数异常");
        }
        return result;
    }

    @RequestMapping(value = "/getLogsByPage", method = RequestMethod.GET)
    public CommonResult<List<LogInfo>> getLogsByPage(@RequestParam("start") int start, @RequestParam("size") int size) {
        CommonResult<List<LogInfo>> result = new CommonResult<>();
        try {
            List<LogInfo> logs = logsService.getLogsByPage(start, size);
            result.setData(logs);
            result.setMsg("获取日志成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("获取日志异常");
        }
        return result;
    }
}
