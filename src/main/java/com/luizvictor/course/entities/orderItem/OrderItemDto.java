package com.luizvictor.course.entities.orderItem;


import java.math.BigDecimal;

public record OrderItemDto(Long id, Long productId, Integer quantity, BigDecimal price, BigDecimal subtotal) {
    public OrderItemDto(OrderItem orderItem) {
        this(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getSubTotal()
        );
    }
}