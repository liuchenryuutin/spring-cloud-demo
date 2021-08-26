package lc.cy.stock.service;

import lc.cy.stock.dto.StockInDto;
import lc.cy.stock.dto.StockOutDto;

public interface StockService {
    public StockOutDto getStockInfo(StockInDto inDto);

    public StockOutDto changeStock(StockInDto inDto);
}
