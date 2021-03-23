package cn.edu.pku.entities;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LogInfo implements Serializable {

    private String ip;

    private String cmd;

    private String timestamp;
}
