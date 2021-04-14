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
            result.setHost(ipAddress);
            result.setCreateTime(new Date((container.getCreated() + 3600 * 8) * 1000));
            ContainerMount containerMount = container.getMounts().get(container.getMounts().size() - 1);
            String logPath = containerMount.getSource();

            results.add(result);
        }
        return results;
    }

    @Override
    public ContainerInfo createContainer(ContainerInfo containerInfo) {
        //dockerClient.pullImageCmd(imageName).withTag("latest").exec(new PullImageResultCallback()).awaitCompletion();
//        Volume volume1 = null;
//        Volume volume2 = null;
//        if (containerInfo.getImage().equals("nginx")) {
//            volume1 = new Volume("/var/log/nginx"); // 容器数据卷映射，日志
//            volume2 = new Volume("/etc/nginx/nginx.conf"); // 配置文件
//        }

        PortBinding portBinding = PortBinding.parse(containerInfo.getPublicPort() + ":" + containerInfo.getPrivatePort());
        HostConfig hostConfig = HostConfig.newHostConfig()
                .withPortBindings(portBinding);
//        List<Bind> binds = new ArrayList<>();
//        if (volume1 != null) {
//            binds.add(new Bind(containerInfo.getLogPath(), volume1));
//            //hostConfig.withBinds(new Bind(containerInfo.getLogPath(), volume1));
//        }
//        if (volume2 != null) {
//            binds.add(new Bind("/opt/container_config/nginx/nginx.conf", volume2));
//            //hostConfig.withBinds(new Bind("/opt/container_config/nginx/nginx.conf", volume2));
//        }
//        hostConfig.withBinds(binds);
//        System.out.println("====================================");
//        System.out.println("log_path:" + containerInfo.getLogPath());
        CreateContainerResponse response = dockerClient.createContainerCmd(containerInfo.getImage())
                .withName(containerInfo.getName())
                .withHostConfig(hostConfig)
                .withExposedPorts(ExposedPort.parse(containerInfo.getPrivatePort() + "/tcp"))
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
