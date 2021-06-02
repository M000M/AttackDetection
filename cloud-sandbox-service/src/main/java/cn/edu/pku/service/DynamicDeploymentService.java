package cn.edu.pku.service;

public interface DynamicDeploymentService {

    int getRunningContainerCountByImageName(String imageName);

    int getRecentAttackCount(String key);
}
