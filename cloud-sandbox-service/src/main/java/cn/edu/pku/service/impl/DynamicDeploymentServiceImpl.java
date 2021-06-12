package cn.edu.pku.service.impl;

import cn.edu.pku.dao.DynamicDeploymentMapper;
import cn.edu.pku.service.DynamicDeploymentService;
import cn.edu.pku.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DynamicDeploymentServiceImpl implements DynamicDeploymentService {

    @Resource
    private DynamicDeploymentMapper dynamicDeploymentMapper;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public int getRunningContainerCountByImageName(String imageName) {
        return dynamicDeploymentMapper.getRunningContainerCountByImageName(imageName);
    }

    @Override
    public int getRecentAttackCount(String key) {
        Object value = redisUtils.get(key);
        if (value != null) {
            return (Integer) value;
        } else {
            return 0;
        }
    }
}
