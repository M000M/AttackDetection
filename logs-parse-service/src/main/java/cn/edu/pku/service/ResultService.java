package cn.edu.pku.service;

import cn.edu.pku.entities.DetectionResult;

import java.util.List;

public interface ResultService {

    List<DetectionResult> getAllResult();

    boolean addResult(String str);
}
