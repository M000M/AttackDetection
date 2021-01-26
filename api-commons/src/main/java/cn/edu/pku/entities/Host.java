package cn.edu.pku.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Host {

    private long id;

    private String ip;

    private int port;

    private int status;

    private int state;
}
