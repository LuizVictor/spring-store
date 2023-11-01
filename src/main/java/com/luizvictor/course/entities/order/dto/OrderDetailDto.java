package com.luizvictor.course.entities.order.dto;

import com.luizvictor.course.entities.order.Order;
import com.luizvictor.course.entities.orderItem.OrderItemDetailDto;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetailDto(Long id, Long userId, List<OrderItemDetailDto> items, BigDecimal total, String status) {
    public OrderDetailDto(Order order) {
        this(
                order.getId(),
                order.getUser().getId(),
                order.getItens().stream().map(OrderItemDetailDto::new).toList(),
                order.getTotal(),
                order.getOrderStatus().name()
        );
    }
}
