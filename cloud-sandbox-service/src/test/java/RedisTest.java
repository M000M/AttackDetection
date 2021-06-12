import cn.edu.pku.CloudSandboxServiceMain;
import cn.edu.pku.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = CloudSandboxServiceMain.class)
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    private RedisUtils redisUtils;

    @Test
    public void test1() {
        String key = "key2";
        String value = "10002";

        boolean res = redisUtils.set(key, value);
        if (res) {
            System.out.println("Operation OK!");
        } else {
            System.out.println("Operation Failure!");
        }
    }
}
