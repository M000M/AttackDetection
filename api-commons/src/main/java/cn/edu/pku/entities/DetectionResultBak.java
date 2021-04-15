package cn.edu.pku.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DetectionResultBak {

    private Long id;           // 数据库ID

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;         // 检测到的攻击时间

    private String ip;         // IP地址

    private String command;    // 入侵者可能执行了的命令

    private String info;       // 相关信息，如是否在试图登录

    private String coreInfo;   // 关键信息，入侵者登录时所使用的用户名和密码

    private String status;     // 入侵者试图入侵的结果，暴力破解的结果

    private String attackType; // 最有可能的入侵类型

    private String conTime;    // 连接持续的时间

    private String other;      // 其他信息
}
