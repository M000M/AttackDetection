package cn.edu.pku.service;

import cn.edu.pku.entities.RegularExpression;

import java.util.List;

public interface ExpressionService {

    List<RegularExpression> getAllExpression();

    boolean addExpression(RegularExpression regularExpression);

    boolean updateExpression(RegularExpression regularExpression);
}
