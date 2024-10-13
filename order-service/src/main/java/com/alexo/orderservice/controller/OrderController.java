package com.alexo.orderservice.controller;

import com.alexo.basedomains.dto.Order;
import com.alexo.basedomains.dto.OrderEvent;
import com.alexo.orderservice.kakfa.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent event = new OrderEvent();
        event.setStatus("PENDING");
        event.setMessage("ORDER PENDING");
        event.setOrder(order);

        orderProducer.sendMessage(event);
        return "order placed successfully";
    }
}
