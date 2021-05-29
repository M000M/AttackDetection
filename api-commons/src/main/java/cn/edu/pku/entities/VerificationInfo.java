package cn.edu.pku.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class VerificationInfo {

    private long logId;

    private String logData;

    private String logHash1;

    private String logHash2;

    private boolean result;
}
