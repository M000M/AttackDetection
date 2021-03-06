package cn.edu.pku.service;

public interface DynamicDeploymentService {

    int getRunningContainerCountByImageName(String imageName);

    int getRecentAttackCount(String key);

    boolean setAttackCountRateByType(String type, int maxRate);
}
