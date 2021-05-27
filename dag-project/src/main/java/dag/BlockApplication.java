package dag;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "dag.dao")
@SpringBootApplication
public class BlockApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlockApplication.class, args);
    }
}
