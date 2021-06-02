package cn.edu.pku.service.impl;

import cn.edu.pku.dao.DynamicDeploymentMapper;
import cn.edu.pku.service.DynamicDeploymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DynamicDeploymentServiceImpl implements DynamicDeploymentService {

    @Resource
    private DynamicDeploymentMapper dynamicDeploymentMapper;

    @Override
    public int getRunningContainerCountByImageName(String imageName) {
        return dynamicDeploymentMapper.getRunningContainerCountByImageName(imageName);
    }
}
