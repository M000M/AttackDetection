package cn.edu.pku.dao;

import cn.edu.pku.entities.Host;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HostsMapper {

    List<Host> allHosts();

    int addHost(Host host);

    int update(Host host);

    Host getHostByIp(@Param("ip") String ip);

    List<Host> activeHosts();
}
