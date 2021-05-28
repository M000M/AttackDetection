package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.service.VerificationLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping(value = "/verification")
public class VerificationLogsController {

    @Resource
    private VerificationLogsService verificationLogsService;

    @RequestMapping(value = "/getLogById", method = RequestMethod.GET)
    public CommonResult<String> getLogById(@RequestParam("id") long id) {
        CommonResult<String> result = new CommonResult<>();
        try {
            String message = verificationLogsService.getLogById(id);
            result.setData(message);
            result.setMsg("根据ID获取日志成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("根据ID获取日志异常");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getLogHashById", method = RequestMethod.GET)
    public CommonResult<String> getLogHashById(@RequestParam("id") long id) {
        CommonResult<String> result = new CommonResult<>();
        try {
            String message = verificationLogsService.getLogHashById(id);
            result.setData(message);
            result.setMsg("根据ID获取日志哈希值成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("根据ID获取日志哈希值异常");
            e.printStackTrace();
        }
        return result;
    }
}
