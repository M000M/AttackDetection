package cn.edu.pku.service.impl;

import cn.edu.pku.dao.DockerContainerMapper;
import cn.edu.pku.dao.DynamicDeploymentMapper;
import cn.edu.pku.service.DockerFeignService;
import cn.edu.pku.service.DynamicDeploymentService;
import cn.edu.pku.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DynamicDeploymentServiceImpl implements DynamicDeploymentService {

    @Resource
    private DynamicDeploymentMapper dynamicDeploymentMapper;

    @Resource
    private DockerFeignService dockerFeignService;

    @Resource
    private RedisUtils redisUtils;

    private int cowrieCount = 0;
    private int cowrieAttackCount = 0;
    private double cowrieRate = 100;

    private int conpotCount = 0;
    private int conpotAttackCount = 0;
    private double conpotRate = 100;

    private int adbhoneyCount = 0;
    private int adbhoneyAttackCount = 0;
    private double adbhoneyRate = 100;

    private int honeytrapCount = 0;
    private int honeytrapAttackCount = 0;
    private double honeytrapRate = 100;

    private int citrixhoneypotCount = 0;
    private int citrixhoneypotAttackCount = 0;
    private double citrixhoneypotRate = 100;

    @Override
    public int getRunningContainerCountByImageName(String imageName) {
        int res = dynamicDeploymentMapper.getRunningContainerCountByImageName(imageName);
        switch (imageName) {
            case "cowrie/cowrie:latest":
                cowrieCount = res;
                break;
            case "conpot:latest":
                conpotCount = res;
                break;
            case "adbhoney:latest":
                adbhoneyCount = res;
                break;
            case "honeytrap/honeytrap:latest":
                honeytrapCount = res;
                break;
            case "citrixhoneypot:latest":
                citrixhoneypotCount = res;
                break;
        }
        return res;
    }

    @Override
    public int getRecentAttackCount(String key) {
        Object value = redisUtils.get(key);
        int res = 0;
        if (value != null) {
            res =  (Integer) value;
        }
        switch (key) {
            case "cowrie":
                cowrieAttackCount = res;
                break;
            case "conpot":
                conpotAttackCount = res;
                break;
            case "adbhoney":
                adbhoneyAttackCount = res;
                break;
            case "honeytrap":
                honeytrapAttackCount = res;
                break;
            case "citrixhoneypot":
                citrixhoneypotAttackCount = res;
                break;
        }
        // 每请求一次就调用一次调整部署
        adjustDeployment();
        return res;
    }

    private void adjustDeployment() {
        // 调整cowrie蜜罐的部署
        if (cowrieAttackCount * 1.0 / cowrieCount > cowrieRate) {
            cowrieCount--;
        }
        // 调整conpot蜜罐的部署
        if (conpotAttackCount * 1.0 / conpotCount > conpotRate) {
            conpotCount--;
        }
        // 调整adbhoney蜜罐的部署
        if (adbhoneyAttackCount * 1.0 / adbhoneyCount > adbhoneyRate) {
            adbhoneyCount--;
        }
        // 调整honeytrap蜜罐的部署
        if (honeytrapAttackCount * 1.0 / honeytrapCount > adbhoneyRate) {
            honeytrapCount--;
        }
        // 调整citrixhoneypot蜜罐的部署
        if (citrixhoneypotAttackCount * 1.0 / citrixhoneypotCount > citrixhoneypotRate) {
            citrixhoneypotCount--;
        }
    }

    private void stopAContainerByType(String type) {

    }

    private void startAContainerByType(String type) {

    }
}
