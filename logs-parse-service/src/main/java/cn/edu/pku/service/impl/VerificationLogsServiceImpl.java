package cn.edu.pku.service.impl;

import cn.edu.pku.dao.VerificationLogsMapper;
import cn.edu.pku.service.VerificationLogsService;
import cn.edu.pku.utils.SHA256Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VerificationLogsServiceImpl implements VerificationLogsService {

    @Resource
    private VerificationLogsMapper verificationLogsMapper;

    @Override
    public boolean addLog(String message) {
        String hash = SHA256Utils.sha256Code(message);
        int res = 0;
        try {
            res = verificationLogsMapper.addLog(message, hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res > 0;
    }

    @Override
    public String getLogById(long id) {
        String message = null;
        try {
            message = verificationLogsMapper.getLogById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public String getLogHashById(long id) {
        String hash = null;
        try {
            hash = verificationLogsMapper.getLogHashById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }
}
