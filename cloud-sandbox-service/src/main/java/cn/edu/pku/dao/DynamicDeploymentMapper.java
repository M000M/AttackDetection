package cn.edu.pku.dao;

import cn.edu.pku.entities.ContainerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DynamicDeploymentMapper {

    int getRunningContainerCountByImageName(String imageName);

    ContainerInfo getARunningContainerByType(String type);

    ContainerInfo getAExitedContainerByType(String type);
}
