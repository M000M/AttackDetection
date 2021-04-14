package cn.edu.pku.controller;

import cn.edu.pku.entities.CommonResult;
import cn.edu.pku.entities.RegularExpression;
import cn.edu.pku.service.ExpressionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/expression")
public class ExpressionController {

    @Resource
    private ExpressionService expressionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<RegularExpression>> getAllExpression() {
        CommonResult<List<RegularExpression>> res = new CommonResult<>();
        try {
            List<RegularExpression> data = expressionService.getAllExpression();
            res.setData(data);
            res.setMsg("获取所有规则成功");
        } catch (Exception e) {
            res.setStatus(false);
            res.setMsg("获取所有规则异常");
        }
        return res;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<Boolean> addExpression(@RequestBody RegularExpression regularExpression) {
        CommonResult<Boolean> res = new CommonResult<>();
        try {
            boolean ans = expressionService.addExpression(regularExpression);
            res.setData(ans);
            res.setMsg("添加规则成功");
        } catch (Exception e) {
            res.setStatus(false);
            res.setMsg("添加规则异常");
        }
        return res;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonResult<Boolean> editExpression(@RequestBody RegularExpression regularExpression) {
        CommonResult<Boolean> res = new CommonResult<>();
        try {
            boolean ans = expressionService.updateExpression(regularExpression);
            res.setData(ans);
            res.setMsg("更新规则成功");
        } catch (Exception e) {
            res.setStatus(false);
            res.setMsg("更新规则异常");
        }
        return res;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonResult<Boolean> removeExpression(@RequestBody RegularExpression regularExpression) {
        CommonResult<Boolean> res = new CommonResult<>();
        try {
            boolean ans = expressionService.updateExpression(regularExpression);
            res.setData(ans);
            res.setMsg("删除规则成功");
        } catch (Exception e) {
            res.setStatus(false);
            res.setMsg("删除规则异常");
        }
        return res;
    }
}
