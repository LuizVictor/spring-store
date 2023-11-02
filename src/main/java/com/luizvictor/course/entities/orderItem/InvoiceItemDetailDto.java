package com.luizvictor.course.entities.orderItem;


import java.math.BigDecimal;

public record InvoiceItemDetailDto(String name, String category, Integer quantity, BigDecimal price, BigDecimal subtotal) {
    public InvoiceItemDetailDto(InvoiceItem invoiceItem) {
        this(
                invoiceItem.getProduct().getName(),
                invoiceItem.getProduct().getCategory().getName(),
                invoiceItem.getQuantity(),
                invoiceItem.getPrice(),
                invoiceItem.getSubTotal()
        );
    }
}