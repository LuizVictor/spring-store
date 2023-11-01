package com.luizvictor.course.resources;

import com.luizvictor.course.entities.order.OrderDetailDto;
import com.luizvictor.course.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailDto>> findAll() {
        List<OrderDetailDto> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDetailDto> findById(@PathVariable Long id) {
        OrderDetailDto order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }
}
