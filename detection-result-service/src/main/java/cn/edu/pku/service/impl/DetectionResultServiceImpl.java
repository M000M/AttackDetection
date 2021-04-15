package cn.edu.pku.service.impl;

import cn.edu.pku.dao.DetectionResultMapper;
import cn.edu.pku.entities.DetectionResult;
import cn.edu.pku.service.DetectionResultService;
import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetectionResultServiceImpl implements DetectionResultService {

    @Resource
    private DetectionResultMapper detectionResultMapper;

    @Override
    public int getTotal() {
        return detectionResultMapper.getTotal();
    }

    @Override
    public List<Object> getAll() {
        List<Object> result = new ArrayList<>();
        List<DetectionResult> list = detectionResultMapper.getAll();
        for (DetectionResult detectionResult: list) {
            String str = detectionResult.getStr();
            Object obj = JSON.parse(str);
            result.add(obj);
        }
        return result;
    }
}
