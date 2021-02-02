package cn.edu.pku.service;

import cn.edu.pku.entities.ContainerInfo;

import java.util.List;

public interface DockerContainerService {

    List<ContainerInfo> listContainers();

    List<ContainerInfo> runningContainers();

    List<ContainerInfo> exitedContainers();

    boolean createContainer(ContainerInfo containerInfo);

    boolean startContainer(ContainerInfo containerInfo);

    boolean stopContainer(ContainerInfo containerInfo);

    boolean removeContainer(ContainerInfo containerInfo);
}
