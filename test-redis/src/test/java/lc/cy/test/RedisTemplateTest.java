package lc.cy.test;

import lc.cy.RedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApplication.class})
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void Test_get_set(){

        redisTemplate.opsForValue().set("redis_Test_1_set","123");
        System.out.println(redisTemplate.opsForValue().get("redis_Test_1_set"));

        stringRedisTemplate.opsForValue().set("str_redis_Test_1_set","456");
        System.out.println(stringRedisTemplate.opsForValue().get("str_redis_Test_1_set"));

    }

    @Test
    public void Test_set_timeout() throws Exception {

        redisTemplate.opsForValue().set("redis_Test_set_timeout","123",10,TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("redis_Test_set_timeout"));
        Thread.sleep(10000);
        System.out.println(redisTemplate.opsForValue().get("redis_Test_set_timeout"));

    }

}
