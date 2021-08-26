package lc.cy.order.dto;

import lc.cy.order.feign.dto.StockOutDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderOutDto {

    private String useId;

    private String orderId;

    private String stockId;

    private String createTime;

    private String orderInfo;

    private StockOutDto stockInfo;


}
