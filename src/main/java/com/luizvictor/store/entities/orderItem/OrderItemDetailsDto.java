package com.luizvictor.store.entities.orderItem;


import java.math.BigDecimal;

public record OrderItemDetailsDto(String name, String category, Integer quantity, BigDecimal price, BigDecimal subtotal) {
    public OrderItemDetailsDto(OrderItem orderItem) {
        this(
                orderItem.getProduct().getName(),
                orderItem.getProduct().getCategory().getName(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.subTotal()
        );
    }
}