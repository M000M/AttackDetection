package cn.edu.pku.entities;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Host {

    private long id;

    private String ip;

    private int port;

    private int status;

    private int state;
}
