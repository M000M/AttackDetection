package cn.edu.pku.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class StatisticsInfo {

    private long id;

    private String ip;

    private int count;

    private String date;
}
