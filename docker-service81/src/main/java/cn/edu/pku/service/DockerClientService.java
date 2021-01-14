package cn.edu.pku.service;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;

import java.util.List;

public interface DockerClientService {

    List<Image> listImages();

    List<Container> listContainers();

    CreateContainerResponse createContainer(String containerName, String imageName, int exposedPort, int bindingPort) throws InterruptedException;

    void startContainer(String containerId);

    void stopContainer(String containerId);

    void removeContainer(String containerId);
}
