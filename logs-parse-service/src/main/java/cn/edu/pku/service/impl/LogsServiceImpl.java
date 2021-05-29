package cn.edu.pku.service.impl;

import cn.edu.pku.dao.LogsMapper;
import cn.edu.pku.entities.LogInfo;
import cn.edu.pku.service.LogsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogsServiceImpl implements LogsService {

    @Resource
    private LogsMapper logsMapper;

    @Override
    public int getTotal() {
        return logsMapper.getTotal();
    }

    @Override
    public List<LogInfo> getLogsByPage(int start, int size) {
        return logsMapper.getLogsByPage(start, size);
    }
}
