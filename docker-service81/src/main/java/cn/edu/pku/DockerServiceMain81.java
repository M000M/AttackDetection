package cn.edu.pku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DockerServiceMain81 {

    public static void main(String[] args) {
        SpringApplication.run(DockerServiceMain81.class, args);
    }
}
