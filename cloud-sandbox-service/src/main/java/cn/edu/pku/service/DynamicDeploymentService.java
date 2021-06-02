package cn.edu.pku.service;

public interface DynamicDeploymentService {

    int getRunningContainerCountByImageName(String imageName);
}
