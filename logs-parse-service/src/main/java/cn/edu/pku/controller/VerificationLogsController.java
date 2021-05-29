package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.VerificationInfo;
import cn.edu.pku.service.DAGFeignService;
import cn.edu.pku.service.VerificationLogsService;
import cn.edu.pku.utils.SHA256Utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/verification")
public class VerificationLogsController {

    @Resource
    private VerificationLogsService verificationLogsService;

    @Resource
    private DAGFeignService dagFeignService;

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
            String hashAddress = verificationLogsService.getLogHashById(id);
            String hash = dagFeignService.readLogHash(hashAddress).getData();
            result.setData(hash);
            result.setMsg("根据ID获取日志哈希值成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("根据ID获取日志哈希值异常");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public CommonResult<String> verify(@RequestBody VerificationInfo verificationInfo) {
        CommonResult<String> result = new CommonResult<>();
        try {
            String hash = SHA256Utils.sha256Code(null);
            result.setData(hash);
            result.setMsg("计算日志hash成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("计算日志hash异常");
            e.printStackTrace();
        }
        return result;
    }
}
