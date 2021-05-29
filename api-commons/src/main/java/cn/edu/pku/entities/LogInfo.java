package cn.edu.pku.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LogInfo {

    private long id;

    private String message;
}
