package cn.edu.pku.dao;

import cn.edu.pku.entities.ContainerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DockerContainerMapper {

    List<ContainerInfo> listContainers(String host);

    List<ContainerInfo> runningContainers(String host);

    List<ContainerInfo> exitedContainers(String host);

    int createContainer(ContainerInfo containerInfo);

    int updateContainer(ContainerInfo containerInfo);
}
