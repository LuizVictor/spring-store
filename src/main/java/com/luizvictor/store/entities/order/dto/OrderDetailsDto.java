package com.luizvictor.store.entities.order.dto;

import com.luizvictor.store.entities.order.Order;
import com.luizvictor.store.entities.orderItem.OrderItemDetailsDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailsDto(Long id, String user, List<OrderItemDetailsDto> items, BigDecimal total, String status, LocalDateTime createdAt) {
    public OrderDetailsDto(Order order) {
        this(
                order.getId(),
                order.getUser().getName(),
                order.getItens().stream().map(OrderItemDetailsDto::new).toList(),
                order.total(),
                order.getOrderStatus().name(),
                order.getCreatedAt()
        );
    }
}
