package cn.edu.pku.entities;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LogInfo implements Serializable {

    private Date time;

    private String log;
}
