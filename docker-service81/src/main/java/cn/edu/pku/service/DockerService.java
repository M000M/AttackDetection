package cn.edu.pku.service;

import cn.edu.pku.entities.ContainerInfo;

import java.util.List;

public interface DockerService {

    List<ContainerInfo> getContainerList();
}
