package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.Host;
import cn.edu.pku.service.HostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/hosts")
public class HostsController {

    @Resource
    private HostsService hostsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<Host>> list() {
        CommonResult<List<Host>> result = new CommonResult<>();
        try {
            List<Host> hosts = hostsService.allHosts();
            result.setMsg("获取全部主机成功");
            result.setData(hosts);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("获取全部主机失败");
        }
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<Boolean> add(@RequestBody Host host) {
        CommonResult<Boolean> result = new CommonResult<>();
        try {
            boolean res = hostsService.addHost(host);
            result.setMsg("添加主机成功");
            result.setData(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("添加主机失败");
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<Boolean> update(@RequestBody Host host) {
        CommonResult<Boolean> result = new CommonResult<>();
        try {
            boolean res = hostsService.update(host);
            result.setMsg("修改主机状态成功");
            result.setData(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("修改主机状态失败");
        }
        return result;
    }
}
