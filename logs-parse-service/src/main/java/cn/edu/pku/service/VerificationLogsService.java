package cn.edu.pku.service;

public interface VerificationLogsService {

    boolean addLog(String message, String hashAddress);

    String getLogById(long id);

    String getLogHashById(long id);
}
