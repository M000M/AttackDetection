package cn.edu.pku.service;

import cn.edu.pku.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "dag-service")
public interface DAGFeignService {

    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    CommonResult<String> addLogHash(@RequestParam("data") String data);

    @RequestMapping(value = "/readData", method = RequestMethod.POST)
    CommonResult<String> readLogHash(@RequestParam("hash") String hash);
}
