package cn.edu.pku.service;

import cn.edu.pku.entities.ContainerInfo;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;

import java.util.List;

public interface DockerService {

    List<ContainerInfo> getContainerList();

    CreateContainerResponse createContainer(String containerName, String imageName);

    List<Image> listImages();

    List<Container> listContainers();
}
