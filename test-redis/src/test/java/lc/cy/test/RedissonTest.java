package lc.cy.test;

import lc.cy.RedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApplication.class})
public class RedissonTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void Test_lock(){
        RLock lock = redissonClient.getLock("Test_1ock");
        try {
            lock.tryLock(100, 2000, TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set("lockcout", "1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    @Test
    public void Test_lock2(){
        RLock lock = redissonClient.getLock("Test_1ock");
        try {
            lock.tryLock(100, 2000, TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set("lockcout", "2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    @Test
    public void Test_lock3(){
        RLock lock = redissonClient.getLock("Test_1ock");
        try {
            if(!lock.isLocked()){
                lock.tryLock(100, 2000, TimeUnit.SECONDS);
            }

            stringRedisTemplate.opsForValue().set("lockcout", "2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

}
