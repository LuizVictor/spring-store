package com.luizvictor.store.resources;

import com.luizvictor.store.entities.order.dto.OrderDetailsDto;
import com.luizvictor.store.entities.order.dto.OrderDto;
import com.luizvictor.store.entities.order.dto.OrderStatusDto;
import com.luizvictor.store.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Orders")
public class OrderResource {
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailsDto>> findAll() {
        List<OrderDetailsDto> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDetailsDto> findById(@PathVariable Long id) {
        OrderDetailsDto orders = orderService.findById(id);
        return ResponseEntity.ok().body(orders);
    }

    @PostMapping
    public ResponseEntity<OrderDetailsDto> save(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
        OrderDetailsDto orders = orderService.save(dto);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(orders.id()).toUri();
        return ResponseEntity.created(uri).body(orders);
    }

    @PatchMapping("{id}")
    public ResponseEntity<OrderDetailsDto> updateStatus(@PathVariable Long id, @RequestBody @Valid OrderStatusDto dto) {
        OrderDetailsDto orders = orderService.updateStatus(id, dto);
        return ResponseEntity.ok().body(orders);
    }
}
