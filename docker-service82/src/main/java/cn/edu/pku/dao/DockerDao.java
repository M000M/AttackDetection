package cn.edu.pku.dao;

import cn.edu.pku.entities.ContainerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DockerDao {

    List<ContainerInfo> getContainerList();

    int createContainer(ContainerInfo containerInfo);
}
