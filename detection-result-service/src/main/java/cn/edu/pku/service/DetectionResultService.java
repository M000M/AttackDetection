package cn.edu.pku.service;

import org.json.JSONObject;

import java.util.List;

public interface DetectionResultService {

    int getTotal();

    List<Object> getResultByPage(int start, int size);
}
