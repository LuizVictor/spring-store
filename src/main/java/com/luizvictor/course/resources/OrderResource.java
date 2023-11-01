package com.luizvictor.course.resources;

import com.luizvictor.course.entities.order.dto.OrderDetailDto;
import com.luizvictor.course.entities.order.dto.OrderDto;
import com.luizvictor.course.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<OrderDetailDto> save(@RequestBody OrderDto orderDto, UriComponentsBuilder uriBuilder) {
        OrderDetailDto order = orderService.save(orderDto);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(order.id()).toUri();
        return ResponseEntity.created(uri).body(order);
    }
}
