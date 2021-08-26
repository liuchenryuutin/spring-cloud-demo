package lc.cy.order.feign.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockOutDto {

    private String stockId;

    private String stockInfo;

    private int stockCount;
}
