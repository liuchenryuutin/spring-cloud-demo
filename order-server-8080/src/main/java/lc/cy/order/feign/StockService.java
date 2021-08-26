package lc.cy.order.feign;

import lc.cy.order.feign.dto.StockInDto;
import lc.cy.order.feign.dto.StockOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient("stock-server")
public interface StockService {

    @PostMapping(path = "/stock/f001/getstockinfo")
    public StockOutDto getStockInnfo(@RequestBody StockInDto inDto);
}
