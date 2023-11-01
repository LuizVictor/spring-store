package com.luizvictor.course.entities.order;

import com.luizvictor.course.entities.orderItem.OrderItemDto;

import java.util.List;

public record OrderDetailDto(Long id, Long user, List<OrderItemDto> items, String status, Long payment) {
    public OrderDetailDto(Order order) {
        this(
                order.getId(),
                order.getUser().getId(),
                order.getItens().stream().map(OrderItemDto::new).toList(),
                order.getOrderStatus().name(),
                order.getPayment() != null ? order.getPayment().getId() : null
        );
    }
}
