package cn.edu.pku.controller;

import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.service.DockerClientService;
import cn.edu.pku.service.DockerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DockerController {

    @Resource
    private DockerService dockerService;

    @RequestMapping(value = "/container/list", method = RequestMethod.GET)
    public List<ContainerInfo> getContainerList() {
        return dockerService.getContainerList();
    }
}
