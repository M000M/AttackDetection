package cn.edu.pku.service;

import cn.edu.pku.entities.ContainerInfo;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Image;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "CLOUD-DOCKER-SERVICE")
public interface DockerFeignService {

    @RequestMapping(value = "/container/images", method = RequestMethod.GET)
    List<Image> listImages();

    @RequestMapping(value = "/container/containers", method = RequestMethod.GET)
    List<ContainerInfo> listContainers();

    @RequestMapping(value = "/container/create", method = RequestMethod.GET)
    CreateContainerResponse createContainer(@RequestParam("containerName") String containerName,
                                            @RequestParam("imageName") String imageName,
                                            @RequestParam("exposedPort") int exposedPort,
                                            @RequestParam("bindingPort") int bindingPort) throws InterruptedException;

    @RequestMapping(value = "/container/start", method = RequestMethod.GET)
    void startContainer(@RequestParam("containerId") String containerId);

    @RequestMapping(value = "/container/stop", method = RequestMethod.GET)
    void stopContainer(@RequestParam("containerId") String containerId);

    @RequestMapping(value = "/container/remove", method = RequestMethod.GET)
    void removeContainer(@RequestParam("containerId") String containerId);
}
