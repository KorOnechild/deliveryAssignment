package com.sparta.delivery.controller;


import com.sparta.delivery.dto.OrderRequestDto;
import com.sparta.delivery.model.Order;
import com.sparta.delivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this. orderService = orderService;
    }

    @PostMapping("/order/request")
    public Order registerOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.registerOrder(orderRequestDto);
    }
}
