package cn.edu.pku.service.impl;

import cn.edu.pku.dao.DockerContainerMapper;
import cn.edu.pku.dao.HostsMapper;
import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.entities.Host;
import cn.edu.pku.service.DockerContainerService;
import cn.edu.pku.service.HostsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HostsServiceImpl implements HostsService {

    @Resource
    private HostsMapper hostsMapper;

    @Resource
    private DockerContainerService dockerContainerService;

    @Resource
    private DockerContainerMapper dockerContainerMapper;

    @Override
    public List<Host> allHosts() {
        return hostsMapper.allHosts();
    }

    @Override
    @Transactional
    public boolean addHost(Host host) {
        Host host1 = hostsMapper.getHostByIp(host.getIp());
        int res = 0;
        if (host1 == null) {
            res = hostsMapper.addHost(host);
        } else {
            host1.setState(1);
            host1.setPort(host.getPort());
            res = hostsMapper.update(host1);
        }
        return res > 0;
    }

    @Override
    @Transactional
    public boolean update(Host host) {
        if (host.getStatus() == 0) {
            List<ContainerInfo> runningContainers = dockerContainerMapper.runningContainers(host.getIp());
            for (ContainerInfo containerInfo : runningContainers) {
                dockerContainerService.stopContainer(containerInfo);
            }
        }
        int res = hostsMapper.update(host);
        return res > 0;
    }

    @Override
    public List<Host> activeHost() {
        return hostsMapper.activeHosts();
    }

    @Override
    public Host getHostByIp(String ip) {
        return hostsMapper.getHostByIp(ip);
    }
}
