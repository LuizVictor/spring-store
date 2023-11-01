package com.luizvictor.course.entities.orderItem;


import java.math.BigDecimal;

public record OrderItemDetailDto(Long id, Long productId, Integer quantity, BigDecimal price, BigDecimal subtotal) {
    public OrderItemDetailDto(OrderItem orderItem) {
        this(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getSubTotal()
        );
    }
}