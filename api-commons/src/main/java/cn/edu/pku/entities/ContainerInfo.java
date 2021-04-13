package cn.edu.pku.entities;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/***
 * 容器信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContainerInfo {

    private long id;            // 数据库ID

    private String name;        // 容器的名字

    private String containerId; // 容器ID

    private String image;       // 对应镜像名称

    private String state;       // 容器目前所处的状态: running=>正在运行

    private int privatePort;    // 容器内部端口

    private int publicPort;     // 容器外部映射端口

    private String host;        // 容器宿主机的IP地址

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;    // 容器创建时间

    private int valid;          // 容器是否被删除：1没有；0被删除
}