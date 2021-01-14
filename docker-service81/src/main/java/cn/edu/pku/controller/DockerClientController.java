package cn.edu.pku.controller;

import cn.edu.pku.service.DockerClientService;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
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
    public List<Container> listContainers() {
        try {
            return dockerClientService.listContainers();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createContainer(@RequestParam(value = "containerName") String containerName,
                                  @RequestParam(value = "imageName") String imageName,
                                  @RequestParam(value = "exposedPort") int exposedPort,
                                  @RequestParam(value = "bindingPort") int bindingPort) {
        try {
            CreateContainerResponse response = dockerClientService.createContainer(containerName, imageName, exposedPort, bindingPort);
            if (response != null) {
                return response.getId();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return "There's some problem in creating the container";
        }
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String startContainer(@RequestParam(value = "containerId") String containerId) {
        try {
            dockerClientService.startContainer(containerId);
            return "Start " + containerId + " Success";
        } catch (Exception e) {
            System.out.println(e);
            return "There's some problem in starting the container " + containerId;
        }
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public String stopContainer(@RequestParam(value = "containerId") String containerId) {
        try {
            dockerClientService.stopContainer(containerId);
            return "Stop " + containerId + " Success";
        } catch (Exception e) {
            System.out.println(e);
            return "There's some problem in stopping the container " + containerId;
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String removeContainer(@RequestParam(value = "containerId") String containerId) {
        try {
            dockerClientService.removeContainer(containerId);
            return "remove " + containerId + " Success";
        } catch (Exception e) {
            System.out.println(e);
            return "There's some problem in removing the container " + containerId;
        }
    }
}
