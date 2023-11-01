package com.luizvictor.course.entities.order;

import com.luizvictor.course.entities.OrderItem;

import java.util.List;

public record OrderDetailDto(Long id, Long user, List<OrderItem> orderItems, String status, Long payment) {
    public OrderDetailDto(Order order) {
        this(
                order.getId(),
                order.getUser().getId(),
                order.getItens(),
                order.getOrderStatus().name(),
                order.getPayment() != null ? order.getPayment().getId() : null
        );
    }
}
