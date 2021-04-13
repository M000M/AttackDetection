package cn.edu.pku.service;

import cn.edu.pku.entities.ContainerInfo;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;

import java.util.List;

public interface DockerClientService {

    List<Image> listImages();

    List<ContainerInfo> listContainers();

    ContainerInfo createContainer(ContainerInfo containerInfo);

    void startContainer(String containerId);

    void stopContainer(String containerId);

    void removeContainer(String containerId);
}
