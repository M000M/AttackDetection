package cn.edu.pku.service.impl;

import cn.edu.pku.dao.HostsMapper;
import cn.edu.pku.entities.Host;
import cn.edu.pku.service.HostsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HostsServiceImpl implements HostsService {

    @Resource
    private HostsMapper hostsMapper;

    @Override
    public List<Host> allHosts() {
        return hostsMapper.allHosts();
    }

    @Override
    public boolean addHost(Host host) {
        Host host1 = hostsMapper.getHostByIp(host.getIp());
        int res = 0;
        if (host1 == null) {
            res = hostsMapper.addHost(host);
        } else {
            host1.setState(1);
            res = hostsMapper.update(host1);
        }
        return res > 0;
    }

    @Override
    public boolean update(Host host) {
        int res = hostsMapper.update(host);
        return res > 0;
    }
}
