package cn.edu.pku.service.impl;

import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.service.DockerClientService;
import cn.edu.pku.utiles.TimeUtils;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.command.InspectContainerCmdImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DockerClientServiceImpl implements DockerClientService {

    @Value("${docker.host}")
    private String dockerHost;

    @Value("${docker.ipAddress}")
    private String ipAddress;

    @Resource
    private DockerClient dockerClient;

    @Override
    public List<Image> listImages() {
        return dockerClient.listImagesCmd().exec();
    }

    @Override
    public List<ContainerInfo> listContainers() {
        List<Container> containers = dockerClient.listContainersCmd().exec();
        List<ContainerInfo> results = new ArrayList<>();
        for (Container container: containers) {
            ContainerInfo result = new ContainerInfo();

            result.setContainerId(container.getId());

            result.setImage(container.getImage());

            result.setName(container.getNames()[0]);

            int privatePort = 0;
            if (container.getPorts()[0].getPrivatePort() != null) {
                privatePort = container.getPorts()[0].getPrivatePort();

            }
            result.setPrivatePort(privatePort);

            int publicPort = 0;
            if (container.getPorts()[0].getPublicPort() != null) {
                publicPort = container.getPorts()[0].getPublicPort();
            }
            result.setPublicPort(publicPort);

            result.setState(container.getState());
            result.setStatus(container.getStatus());
            result.setHost(ipAddress);
            result.setCreateTime(new Date(container.getCreated() * 1000));

            results.add(result);
        }
        return results;
    }

    /***
     * 创建容器
     * @param containerName
     * @param imageName
     * @return
     */
    @Override
    public CreateContainerResponse createContainer(String containerName, String imageName, int exposedPort, int bindingPort) throws InterruptedException {
        dockerClient.pullImageCmd(imageName).withTag("latest").exec(new PullImageResultCallback()).awaitCompletion();
        //映射端口 8088->80
        ExposedPort tcp80 = ExposedPort.tcp(exposedPort);
        Ports portBinding = new Ports();
        portBinding.bind(tcp80, Ports.Binding.bindPort(bindingPort));

        CreateContainerResponse response = dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(HostConfig.newHostConfig().withPortBindings(portBinding))
                .withExposedPorts(tcp80)
                .exec();
        startContainer(response.getId());
        InspectContainerResponse inspect = dockerClient.inspectContainerCmd(response.getId()).exec();
        inspect.getName();
        return response;
    }

    /***
     * 运行已经创建的容器
     * @param containerId
     */
    @Override
    public void startContainer(String containerId) {
        dockerClient.startContainerCmd(containerId).exec();
    }

    /***
     * 停止运行的容器
     * @param containerId
     */
    @Override
    public void stopContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }

    /***
     * 删除容器
     * @param containerId
     */
    @Override
    public void removeContainer(String containerId) {
        dockerClient.removeContainerCmd(containerId).exec();
    }
}
