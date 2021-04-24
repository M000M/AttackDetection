package cn.edu.pku.entities;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class NginxLogInfo implements Serializable {

    private String remoteAddr;

    private String user;

    private String accessTime;

    private String httpHost;

    private String requestMethod;

    private String uri;

    private String param;

    private String httpStatus;

    private String bodyBytesSent;

    private String httpReferrer;

    private String upstreamStatus;

    private String upstreamAddr;

    private String upstreamResponseTime;

    private String requestTime;

    private String userAgent;

    private String xForwardFor;
}
