package cn.edu.pku.service;

import cn.edu.pku.entities.Host;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HostsService {

    List<Host> allHosts();

    boolean addHost(Host host);

    boolean update(Host host);
}
