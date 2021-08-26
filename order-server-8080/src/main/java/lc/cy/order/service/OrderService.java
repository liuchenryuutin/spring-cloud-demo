package lc.cy.order.service;

import lc.cy.order.dto.OrderInDto;
import lc.cy.order.dto.OrderOutDto;

public interface OrderService {
    public OrderOutDto getOrderInfo(OrderInDto inDto);
}
