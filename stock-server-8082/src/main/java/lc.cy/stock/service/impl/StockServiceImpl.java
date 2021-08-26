package lc.cy.stock.service.impl;

import lc.cy.stock.dto.StockInDto;
import lc.cy.stock.dto.StockOutDto;
import lc.cy.stock.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StockServiceImpl implements StockService {

    @Value("${server.port}")
    private String server;

    private AtomicInteger count = new AtomicInteger(20000);

    public StockOutDto getStockInfo(StockInDto inDto){
        StockOutDto result = new StockOutDto();
        result.setStockId(inDto.getStockId());
        result.setStockInfo("Server(" + server + "):风扇还有，可以下单");
        result.setStockCount(count.get());
        return result;
    }

    public StockOutDto changeStock(StockInDto inDto){
        StockOutDto result = new StockOutDto();
        result.setStockId(inDto.getStockId());
        result.setStockInfo("Server(" + server + "):库存更新！");
        result.setStockCount(count.addAndGet(inDto.getSubtract()));
        return result;
    }
}
