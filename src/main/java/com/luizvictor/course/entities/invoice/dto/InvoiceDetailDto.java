package com.luizvictor.course.entities.invoice.dto;

import com.luizvictor.course.entities.invoice.Invoice;
import com.luizvictor.course.entities.orderItem.InvoiceItemDetailDto;

import java.math.BigDecimal;
import java.util.List;

public record InvoiceDetailDto(Long id, Long userId, List<InvoiceItemDetailDto> items, BigDecimal total, String status) {
    public InvoiceDetailDto(Invoice invoice) {
        this(
                invoice.getId(),
                invoice.getUser().getId(),
                invoice.getItens().stream().map(InvoiceItemDetailDto::new).toList(),
                invoice.getTotal(),
                invoice.getInvoiceStatus().name()
        );
    }
}
