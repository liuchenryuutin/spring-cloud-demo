package lc.cy.order.service.impl;

import lc.cy.order.dto.OrderInDto;
import lc.cy.order.dto.OrderOutDto;
import lc.cy.order.feign.StockService;
import lc.cy.order.feign.dto.StockInDto;
import lc.cy.order.feign.dto.StockOutDto;
import lc.cy.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StockService stockService;

    public OrderOutDto getOrderInfo(OrderInDto inDto) {
        OrderOutDto out = new OrderOutDto();
        out.setUseId(inDto.getUsrId());
        out.setStockId(inDto.getStockId());
        out.setOrderId(inDto.getOrderId());
        out.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS")));

        // 调用库存服务
        StockInDto skInDto = new StockInDto();
        skInDto.setStockId(inDto.getStockId());
        StockOutDto stockOutDto = stockService.getStockInnfo(skInDto);
        out.setStockInfo(stockOutDto);
        return out;
    }
}
