package cn.edu.pku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DockerServiceMain80 {

    public static void main(String[] args) {
        SpringApplication.run(DockerServiceMain80.class, args);
    }
}
