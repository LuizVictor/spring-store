package com.luizvictor.store.entities.order.dto;

import com.luizvictor.store.entities.order.Order;
import com.luizvictor.store.entities.orderItem.OrderItemDetailDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailDto(Long id, String user, List<OrderItemDetailDto> items, BigDecimal total, String status, LocalDateTime createdAt) {
    public OrderDetailDto(Order order) {
        this(
                order.getId(),
                order.getUser().getName(),
                order.getItens().stream().map(OrderItemDetailDto::new).toList(),
                order.getTotal(),
                order.getOrderStatus().name(),
                order.getCreatedAt()
        );
    }
}
