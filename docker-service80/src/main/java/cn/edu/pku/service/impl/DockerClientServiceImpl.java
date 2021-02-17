package cn.edu.pku.service.impl;

import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.service.DockerClientService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
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
        List<Container> containers = dockerClient.listContainersCmd()
                .withShowAll(true)
                .exec();
        List<ContainerInfo> results = new ArrayList<>();
        for (Container container : containers) {
            ContainerInfo result = new ContainerInfo();

            result.setContainerId(container.getId());

            result.setImage(container.getImage());

            result.setName(container.getNames()[0]);

            int privatePort = 0;
            int publicPort = 0;
            if (container.getPorts().length > 0 && container.getPorts()[0].getPrivatePort() != null && container.getPorts()[0].getPublicPort() != null) {
                privatePort = container.getPorts()[0].getPrivatePort();
                publicPort = container.getPorts()[0].getPublicPort();
            }
            result.setPrivatePort(privatePort);
            result.setPublicPort(publicPort);
            result.setState(container.getState());
            result.setStatus(container.getStatus());
            result.setHost(ipAddress);
            result.setCreateTime(new Date((container.getCreated() + 3600 * 8) * 1000));
            ContainerMount containerMount = container.getMounts().get(container.getMounts().size() - 1);
            String logPath = containerMount.getSource();
            result.setLogPath(logPath);

            results.add(result);
        }
        return results;
    }

    @Override
    public ContainerInfo createContainer(ContainerInfo containerInfo) {
        //dockerClient.pullImageCmd(imageName).withTag("latest").exec(new PullImageResultCallback()).awaitCompletion();
        //映射端口 8088->80
        ExposedPort tcpPort = ExposedPort.tcp(containerInfo.getPublicPort());
        Ports portBinding = new Ports();
        portBinding.bind(tcpPort, Ports.Binding.bindPort(containerInfo.getPrivatePort()));

        Volume volume = new Volume(containerInfo.getLogPath());
        CreateContainerResponse response = dockerClient.createContainerCmd(containerInfo.getImage())
                .withName(containerInfo.getName())
                .withHostConfig(HostConfig.newHostConfig().withPortBindings(portBinding))
                .withExposedPorts(tcpPort)
                .withHostConfig(HostConfig.newHostConfig().withBinds(new Bind(containerInfo.getLogPath(), volume)))
                .exec();
        startContainer(response.getId());

        containerInfo.setHost(ipAddress);
        containerInfo.setContainerId(response.getId());

        return containerInfo;
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
