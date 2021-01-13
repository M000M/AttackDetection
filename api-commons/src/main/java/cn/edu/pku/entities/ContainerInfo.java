package cn.edu.pku.entities;

import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/***
 * 容器信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerInfo {

    private long id;

    private String containerId;

    private String image;

    private String name;

    private String createTime;
}
