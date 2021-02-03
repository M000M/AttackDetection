package cn.edu.pku.service.impl;

import cn.edu.pku.dao.DockerContainerMapper;
import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.entities.Host;
import cn.edu.pku.service.DockerContainerService;
import cn.edu.pku.service.DockerFeignService;
import cn.edu.pku.service.HostsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DockerContainerServiceImpl implements DockerContainerService {

    private static final String RUNNING = "Running"; // 容器状态：运行
    private static final String EXCITED = "Exited";  // 容器状态：退出
    private static final int VALID = 1;              // 容器是否存在：是
    private static final int INVALID = 0;            // 容器是否存在：被删除

    @Resource
    private DockerContainerMapper dockerContainerMapper;

    @Resource
    private DockerFeignService dockerFeignService;

    @Resource
    private HostsService hostsService;

    @Resource
    private RestTemplate restTemplate;

    @Value("${host.serviceAddress}")
    private String hostAddress;

    @Override
    public List<ContainerInfo> listContainers() {
        List<Host> hosts = hostsService.activeHost();
        List<ContainerInfo> results = new ArrayList<>();
        for (Host host: hosts) {
            List<ContainerInfo> result = dockerContainerMapper.listContainers(host.getIp());
            results.addAll(result);
        }
        return results;
    }

    @Override
    public List<ContainerInfo> runningContainers() {
        List<Host> hosts = hostsService.activeHost();
        List<ContainerInfo> results = new ArrayList<>();
        for (Host host: hosts) {
            List<ContainerInfo> result = dockerContainerMapper.runningContainers(host.getIp());
            results.addAll(result);
        }
        return results;
    }

    @Override
    public List<ContainerInfo> exitedContainers() {
        List<Host> hosts = hostsService.activeHost();
        List<ContainerInfo> results = new ArrayList<>();
        for (Host host: hosts) {
            List<ContainerInfo> result = dockerContainerMapper.exitedContainers(host.getIp());
            results.addAll(result);
        }
        return results;
    }

    @Override
    @Transactional
    public boolean createContainer(ContainerInfo containerInfo) {
        containerInfo.setLogPath("/log/" + containerInfo.getName());
        // 调用微服务在宿主机上创建容器
        ContainerInfo result = dockerFeignService.createContainer(containerInfo);
        if (result == null || result.getContainerId() == null) {
            return false;
        }
        result.setState(RUNNING);
        result.setCreateTime(new Date());
        result.setValid(VALID);

        // 将创建的容器信息写入到数据库中，方便后续查找
        int res = dockerContainerMapper.createContainer(result);
        return res > 0;
    }

    @Override
    @Transactional
    public boolean startContainer(ContainerInfo containerInfo) {
        Host host = hostsService.getHostByIp(containerInfo.getHost());
        int servicePort = host.getPort();
        String serviceAddress = hostAddress + servicePort + "/container/start/?containerId=" + containerInfo.getContainerId();
        Boolean res = restTemplate.getForObject(serviceAddress, Boolean.class);
        containerInfo.setState(RUNNING); // 标记容器状态为Running
        int res1 = dockerContainerMapper.updateContainer(containerInfo);
        if (res == null || !res || res1 == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    @Transactional
    public boolean stopContainer(ContainerInfo containerInfo) {
        Host host = hostsService.getHostByIp(containerInfo.getHost());
        int servicePort = host.getPort();
        String serviceAddress = hostAddress + servicePort + "/container/stop/?containerId=" + containerInfo.getContainerId();
        Boolean res = restTemplate.getForObject(serviceAddress, Boolean.class);
        containerInfo.setState(EXCITED); // 标记容器状态为Exited
        int res1 = dockerContainerMapper.updateContainer(containerInfo);
        if (res == null || !res || res1 == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    @Transactional
    public boolean removeContainer(ContainerInfo containerInfo) {
        if (containerInfo.getState().equals(RUNNING)) {
            boolean res = stopContainer(containerInfo);
            if (!res) {
                return false;
            }
        }
        Host host = hostsService.getHostByIp(containerInfo.getHost());
        int servicePort = host.getPort();
        String serviceAddress = hostAddress + servicePort + "/container/remove/?containerId=" + containerInfo.getContainerId();
        Boolean res1 = restTemplate.getForObject(serviceAddress, Boolean.class);
        containerInfo.setValid(INVALID); //标记容器被删除
        int res2 = dockerContainerMapper.updateContainer(containerInfo);
        if (res1 == null || !res1 || res2 == 0) {
            return false;
        } else {
            return true;
        }
    }
}
