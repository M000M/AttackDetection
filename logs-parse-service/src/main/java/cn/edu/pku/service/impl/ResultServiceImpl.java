package cn.edu.pku.service.impl;

import cn.edu.pku.dao.ResultMapper;
import cn.edu.pku.entities.DetectionResult;
import cn.edu.pku.service.ResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Resource
    private ResultMapper resultMapper;

    @Override
    public List<DetectionResult> getAllResult() {
        return resultMapper.getAllResult();
    }

    @Override
    public boolean addResult(String str) {
        int res = resultMapper.addResult(str);
        return res > 0;
    }
}
