package lc.cy.stock.controller;

import lc.cy.stock.dto.StockInDto;
import lc.cy.stock.dto.StockOutDto;
import lc.cy.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/f001")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/getstockinfo")
    public StockOutDto getStockInfo(@RequestBody StockInDto inDto){
        StockOutDto result = stockService.getStockInfo(inDto);
        return result;
    }

    @PostMapping("/changestock")
    public StockOutDto changeStock(@RequestBody StockInDto inDto){
        StockOutDto result = stockService.changeStock(inDto);
        return result;
    }
}
