package cn.edu.pku.controller;

import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.service.DockerService;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
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

    @RequestMapping(value = "/container/create", method = RequestMethod.GET)
    public CreateContainerResponse create(@RequestParam(value = "containerName") String containerName,
                                          @RequestParam(value = "imageName") String imageName) {
        CreateContainerResponse response = dockerService.createContainer(containerName, imageName);
        if (response != null) {
            return response;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/container/images", method = RequestMethod.GET)
    public List<Image> imageList() {
        return dockerService.listImages();
    }

    @RequestMapping(value = "/container/containers", method = RequestMethod.GET)
    public List<Container> listContainers() {
        return dockerService.listContainers();
    }
}
