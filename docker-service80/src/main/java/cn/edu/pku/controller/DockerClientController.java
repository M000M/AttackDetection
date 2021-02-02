package cn.edu.pku.controller;

import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.service.DockerClientService;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/container")
public class DockerClientController {

    @Resource
    private DockerClientService dockerClientService;

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public List<Image> imageList() {
        try {
            return dockerClientService.listImages();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping(value = "/containers", method = RequestMethod.GET)
    public List<ContainerInfo> listContainers() {
        try {
            return dockerClientService.listContainers();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ContainerInfo createContainer(@RequestBody ContainerInfo containerInfo) {
        try {
            containerInfo = dockerClientService.createContainer(containerInfo);
            if (containerInfo.getContainerId() != null) {
                return containerInfo;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public Boolean startContainer(@RequestParam(value = "containerId") String containerId) {
        try {
            dockerClientService.startContainer(containerId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public Boolean stopContainer(@RequestParam(value = "containerId") String containerId) {
        try {
            dockerClientService.stopContainer(containerId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public Boolean removeContainer(@RequestParam(value = "containerId") String containerId) {
        try {
            dockerClientService.removeContainer(containerId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
