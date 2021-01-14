package cn.edu.pku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DockerServiceMain82 {

    public static void main(String[] args) {
        SpringApplication.run(DockerServiceMain82.class, args);
    }
}
