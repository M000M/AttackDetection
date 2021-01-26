package cn.edu.pku;

import cn.edu.pku.entities.Host;
import cn.edu.pku.service.HostsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CloudSandboxServiceMainTest {

    @Resource
    private HostsService hostsService;

    @Test
    public void testAllHosts() {
        List<Host> hosts = hostsService.allHosts();
        System.out.println(hosts);
    }

    @Test
    public void testAdd() {
        Host host = new Host();
        host.setIp("192.168.1.85");
        host.setStatus(1);
        boolean res = hostsService.addHost(host);
        System.out.println(res);
    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testGetHostById() {

    }
}
