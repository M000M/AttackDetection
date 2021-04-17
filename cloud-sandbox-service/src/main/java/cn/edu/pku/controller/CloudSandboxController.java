package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.service.DockerContainerService;
import cn.edu.pku.service.DockerFeignService;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/sandbox")
public class CloudSandboxController {

    @Resource
    private DockerContainerService dockerContainerService;

    @RequestMapping(value = "/allContainers", method = RequestMethod.GET)
    public CommonResult<List<ContainerInfo>> allContainers() {
        CommonResult<List<ContainerInfo>> result = new CommonResult<>();
        try {
            List<ContainerInfo> containers = dockerContainerService.listContainers();
            result.setData(containers);
            result.setMsg("查询所有容器成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("查询所有容器异常");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/runningContainers", method = RequestMethod.GET)
    public CommonResult<List<ContainerInfo>> runningContainers() {
        CommonResult<List<ContainerInfo>> result = new CommonResult<>();
        try {
            List<ContainerInfo> containers = dockerContainerService.runningContainers();
            result.setData(containers);
            result.setMsg("查询运行的容器成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("查询运行的容器异常");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/exitedContainers", method = RequestMethod.GET)
    public CommonResult<List<ContainerInfo>> exitedContainers() {
        CommonResult<List<ContainerInfo>> result = new CommonResult<>();
        try {
            List<ContainerInfo> containers = dockerContainerService.exitedContainers();
            result.setData(containers);
            result.setMsg("查询退出的容器成功");
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg("查询退出的容器异常");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/createContainer", method = RequestMethod.POST)
    public CommonResult<Boolean> createContainer(@RequestBody ContainerInfo containerInfo) {
        CommonResult<Boolean> result = new CommonResult<>();
        try {
            boolean res = dockerContainerService.createContainer(containerInfo);
            if (res) {
                result.setData(true);
                result.setMsg("创建容器成功");
            } else {
                result.setData(false);
                result.setMsg("创建容器失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("创建容器异常");
        }
        return result;
    }

    @RequestMapping(value = "/startContainer", method = RequestMethod.POST)
    public CommonResult<Boolean> startContainer(@RequestBody ContainerInfo containerInfo) {
        CommonResult<Boolean> result = new CommonResult<>();
        try {
            boolean res = dockerContainerService.startContainer(containerInfo);
            if (res) {
                result.setData(true);
                result.setMsg("启动容器成功");
            } else {
                result.setData(false);
                result.setMsg("启动容器失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("启动容器异常");
        }
        return result;
    }

    @RequestMapping(value = "/stopContainer", method = RequestMethod.POST)
    public CommonResult<Boolean> stopContainer(@RequestBody ContainerInfo containerInfo) {
        CommonResult<Boolean> result = new CommonResult<>();
        try {
            boolean res = dockerContainerService.stopContainer(containerInfo);
            if (res) {
                result.setData(true);
                result.setMsg("停止容器成功");
            } else {
                result.setData(false);
                result.setMsg("停止容器失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("停止容器异常");
        }
        return result;
    }

    @RequestMapping(value = "/removeContainer", method = RequestMethod.POST)
    public CommonResult<Boolean> removeContainer(@RequestBody ContainerInfo containerInfo) {
        CommonResult<Boolean> result = new CommonResult<>();
        try {
            boolean res = dockerContainerService.removeContainer(containerInfo);
            if (res) {
                result.setData(true);
                result.setMsg("删除容器成功");
            } else {
                result.setData(false);
                result.setMsg("删除容器失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("删除容器异常");
        }
        return result;
    }
}
