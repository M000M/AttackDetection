package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.NginxLogInfo;
import cn.edu.pku.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/es")
public class ElasticsearchController {

    @Resource
    private ElasticsearchService searchService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public CommonResult<List<NginxLogInfo>> getAll() {
        CommonResult<List<NginxLogInfo>> result = new CommonResult<>();
        try {
            List<NginxLogInfo> res = searchService.getAll();
            result.setData(res);
            result.setMsg("查询所有日志成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("查询日志异常");
        }
        return result;
    }

    /***
     * 获取日志的数量
     * @return
     */
    @RequestMapping(value = "/getTotal", method=RequestMethod.GET)
    public CommonResult<Long> getTotal() {
        CommonResult<Long> result = new CommonResult<>();
        try {
            Long res = searchService.getTotal();
            result.setData(res);
            result.setMsg("获取日志数量成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("查询日志数量异常");
        }
        return result;
    }

    @RequestMapping(value = "/getLogByPage", method = RequestMethod.GET)
    public CommonResult<List<Object>> getLogByPage(@RequestParam("start") int start, @RequestParam("size") int size) {
        CommonResult<List<Object>> result = new CommonResult<>();
        try {
            List<Object> res = searchService.getLogByPage(start, size);
            result.setData(res);
            result.setMsg("根据页面获取日志成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("根据页面获取日志异常");
        }
        return result;
    }
}
