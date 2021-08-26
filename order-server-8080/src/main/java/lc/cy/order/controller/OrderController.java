package lc.cy.order.controller;

import lc.cy.order.dto.OrderInDto;
import lc.cy.order.dto.OrderOutDto;
import lc.cy.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/f001")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getinfo")
    public OrderOutDto getOrderInfo(@ModelAttribute OrderInDto inDto){

        OrderOutDto outDto = orderService.getOrderInfo(inDto);
        return outDto;
    }
}
