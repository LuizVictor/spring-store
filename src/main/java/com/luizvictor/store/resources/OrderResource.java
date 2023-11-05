package com.luizvictor.store.resources;

import com.luizvictor.store.entities.order.dto.OrderDetailDto;
import com.luizvictor.store.entities.order.dto.OrderDto;
import com.luizvictor.store.entities.order.dto.OrderStatusDto;
import com.luizvictor.store.services.OrderService;
import jakarta.validation.Valid;
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
        List<OrderDetailDto> invoices = orderService.findAll();
        return ResponseEntity.ok().body(invoices);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDetailDto> findById(@PathVariable Long id) {
        OrderDetailDto invoice = orderService.findById(id);
        return ResponseEntity.ok().body(invoice);
    }

    @PostMapping
    public ResponseEntity<OrderDetailDto> save(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
        OrderDetailDto invoice = orderService.save(dto);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(invoice.id()).toUri();
        return ResponseEntity.created(uri).body(invoice);
    }

    @PatchMapping("{id}")
    public ResponseEntity<OrderDetailDto> updateStatus(@PathVariable Long id, @RequestBody @Valid OrderStatusDto dto) {
        OrderDetailDto invoice = orderService.updateStatus(id, dto);
        return ResponseEntity.ok().body(invoice);
    }
}
