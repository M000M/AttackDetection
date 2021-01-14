package cn.edu.pku.controller;

import cn.edu.pku.service.DockerFeignService;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/sandbox")
public class CloudSandboxController {

    @Resource
    private DockerFeignService dockerFeignService;

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public List<Image> listImages() {
        try {
            return dockerFeignService.listImages();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping(value = "/containers", method = RequestMethod.GET)
    public List<Container> listContainers() {
        try {
            return dockerFeignService.listContainers();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public CreateContainerResponse createContainer(String containerName, String imageName,
                                                   int exposedPort, int bindingPort) throws InterruptedException {
        try {
            return dockerFeignService.createContainer(containerName, imageName, exposedPort, bindingPort);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void startContainer(String containerId) {
        try {
            dockerFeignService.startContainer(containerId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public void stopContainer(String containerId) {
        try {
            dockerFeignService.stopContainer(containerId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public void removeContainer(String containerId) {
        try {
            dockerFeignService.removeContainer(containerId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
