package cn.edu.pku.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DynamicDeploymentMapper {

    int getRunningContainerCountByImageName(String imageName);
}
