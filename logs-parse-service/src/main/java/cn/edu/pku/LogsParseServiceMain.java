package cn.edu.pku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LogsParseServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(LogsParseServiceMain.class, args);
    }
}
