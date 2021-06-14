package cn.edu.pku.service.impl;

import cn.edu.pku.dao.DockerContainerMapper;
import cn.edu.pku.dao.DynamicDeploymentMapper;
import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.service.DockerContainerService;
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
    private DockerContainerService dockerContainerService;

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
            res = (Integer) value;
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
        if (cowrieCount == 0) {
            try {
                startAContainerByType("cowrie/cowrie:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (cowrieAttackCount * 1.0 / cowrieCount > cowrieRate) {
            try {
                stopAContainerByType("cowrie/cowrie:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                startAContainerByType("cowrie/cowrie:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 调整conpot蜜罐的部署
        if (conpotCount == 0) {
            try {
                startAContainerByType("conpot:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (conpotAttackCount * 1.0 / conpotCount > conpotRate) {
            try {
                stopAContainerByType("conpot:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                startAContainerByType("conpot:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 调整adbhoney蜜罐的部署
        if (adbhoneyCount == 0) {
            try {
                startAContainerByType("adbhoney:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (adbhoneyAttackCount * 1.0 / adbhoneyCount > adbhoneyRate) {
            try {
                stopAContainerByType("adbhoney:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                startAContainerByType("adbhoney:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 调整honeytrap蜜罐的部署
        if (honeytrapCount == 0) {
            try {
                startAContainerByType("honeytrap/honeytrap:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (honeytrapAttackCount * 1.0 / honeytrapCount > adbhoneyRate) {
            try {
                stopAContainerByType("honeytrap/honeytrap:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                startAContainerByType("honeytrap/honeytrap:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 调整citrixhoneypot蜜罐的部署
        if (citrixhoneypotCount == 0) {
            try {
                startAContainerByType("citrixhoneypot:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (citrixhoneypotAttackCount * 1.0 / citrixhoneypotCount > citrixhoneypotRate) {
            try {
                stopAContainerByType("citrixhoneypot:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                startAContainerByType("citrixhoneypot:latest");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stopAContainerByType(String type) {
        ContainerInfo containerInfo = dynamicDeploymentMapper.getARunningContainerByType(type);
        if (containerInfo != null && containerInfo.getContainerId() != null) {
            dockerContainerService.stopContainer(containerInfo);
        }
    }

    private void startAContainerByType(String type) {
        ContainerInfo containerInfo = dynamicDeploymentMapper.getAExitedContainerByType(type);
        if (containerInfo != null && containerInfo.getContainerId() != null) {
            dockerContainerService.startContainer(containerInfo);
        }
    }

    @Override
    public boolean setAttackCountRateByType(String type, int maxRate) {
        switch (type) {
            case "cowrie":
                cowrieRate = maxRate;
                break;
            case "conpot":
                conpotRate = maxRate;
                break;
            case "adbhoney":
                adbhoneyRate = maxRate;
                break;
            case "honeytrap":
                honeytrapRate = maxRate;
                break;
            case "citrixhoneypot":
                citrixhoneypotRate = maxRate;
                break;
        }
        return true;
    }
}
