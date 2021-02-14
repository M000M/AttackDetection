package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.NginxLogInfo;
import cn.edu.pku.service.ElasticSearchService;
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
@RequestMapping(value = "/es")
public class ElasticSearchController {

    @Resource
    private ElasticSearchService searchService;

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
}
