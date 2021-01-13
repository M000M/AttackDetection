package cn.edu.pku.service.impl;

import cn.edu.pku.dao.DockerDao;
import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.service.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DockerServiceImpl implements DockerService {

    @Resource
    private DockerDao dockerDao;

    @Value("${docker.host}")
    private String dockerHost;

    @Resource
    private DockerClient dockerClient;

    @Override
    public List<ContainerInfo> getContainerList() {
        return dockerDao.getContainerList();
    }

    /***
     * 创建容器
     * @param containerName
     * @param imageName
     * @return
     */
    public CreateContainerResponse createContainer(String containerName, String imageName) {
        //映射端口 8088->80
        ExposedPort tcp80 = ExposedPort.tcp(80);
        Ports portBinding = new Ports();
        portBinding.bind(tcp80, Ports.Binding.bindPort(8091));

        CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(HostConfig.newHostConfig().withPortBindings(portBinding))
                .withExposedPorts(tcp80)
                .withCmd("/bin/sh")
                .exec();
        return container;
    }

    public List<Image> listImages() {
        return dockerClient.listImagesCmd().exec();
    }

    public List<Container> listContainers() {
        return dockerClient.listContainersCmd().exec();
    }

    public void startContainer(Container container) {
        dockerClient.startContainerCmd(container.getId());
    }
}
