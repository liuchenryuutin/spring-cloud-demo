package lc.cy.stock.service.impl;

import lc.cy.stock.dto.StockInDto;
import lc.cy.stock.dto.StockOutDto;
import lc.cy.stock.service.StockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class StockServiceImpl implements StockService {

    @Value("${server.port}")
    private String server;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate strRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    public StockOutDto getStockInfo(StockInDto inDto){
        StockOutDto result = new StockOutDto();
        result.setStockId(inDto.getStockId());
        result.setStockInfo("Server(" + server + "):风扇还有，可以下单");
        Long count = Long.valueOf(strRedisTemplate.opsForValue().get("fengshan"));
        result.setStockCount(count);
        return result;
    }

    public StockOutDto changeStock(StockInDto inDto){
        StockOutDto result = new StockOutDto();
        result.setStockId(inDto.getStockId());
        result.setStockInfo("Server(" + server + "):库存更新！");
        RLock lock = redissonClient.getLock("change");
        try {
            lock.tryLock(5,5, TimeUnit.SECONDS);
            result.setStockCount(strRedisTemplate.opsForValue().increment("fengshan",inDto.getSubtract()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }
}
