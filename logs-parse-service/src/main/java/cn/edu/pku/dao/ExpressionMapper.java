package cn.edu.pku.dao;

import cn.edu.pku.entities.RegularExpression;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExpressionMapper {

    List<RegularExpression> getAllExpression();

    int addExpression(RegularExpression regularExpression);

    int updateExpression(RegularExpression regularExpression);
}
