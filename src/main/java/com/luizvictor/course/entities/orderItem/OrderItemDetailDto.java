package com.luizvictor.course.entities.orderItem;


import java.math.BigDecimal;

public record OrderItemDetailDto(String name, String category, Integer quantity, BigDecimal price, BigDecimal subtotal) {
    public OrderItemDetailDto(OrderItem orderItem) {
        this(
                orderItem.getProduct().getName(),
                orderItem.getProduct().getCategory().getName(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getSubTotal()
        );
    }
}