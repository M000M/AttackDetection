package cn.edu.pku.service.impl;

import cn.edu.pku.dao.ExpressionMapper;
import cn.edu.pku.entities.RegularExpression;
import cn.edu.pku.service.ExpressionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExpressionServiceImpl implements ExpressionService {

    @Resource
    private ExpressionMapper expressionMapper;

    @Override
    public List<RegularExpression> getAllExpression() {
        return expressionMapper.getAllExpression();
    }

    @Override
    public boolean addExpression(RegularExpression regularExpression) {
        int res = expressionMapper.addExpression(regularExpression);
        return res > 0;
    }

    @Override
    public boolean updateExpression(RegularExpression regularExpression) {
        int res = expressionMapper.updateExpression(regularExpression);
        return res > 0;
    }
}
