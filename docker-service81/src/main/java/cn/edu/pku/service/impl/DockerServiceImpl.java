package cn.edu.pku.service.impl;

import cn.edu.pku.dao.DockerDao;
import cn.edu.pku.entities.ContainerInfo;
import cn.edu.pku.service.DockerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DockerServiceImpl implements DockerService {

    @Resource
    private DockerDao dockerDao;

    @Override
    public List<ContainerInfo> getContainerList() {
        return dockerDao.getContainerList();
    }
}
