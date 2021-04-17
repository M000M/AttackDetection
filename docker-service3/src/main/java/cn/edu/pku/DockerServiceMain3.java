package cn.edu.pku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
public class DockerServiceMain3 {

    public static void main(String[] args) {
        SpringApplication.run(DockerServiceMain3.class, args);
    }
}
