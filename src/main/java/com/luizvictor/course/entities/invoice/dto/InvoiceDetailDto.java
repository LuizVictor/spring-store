package com.luizvictor.course.entities.invoice.dto;

import com.luizvictor.course.entities.invoice.Invoice;
import com.luizvictor.course.entities.orderItem.InvoiceItemDetailDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceDetailDto(Long id, String user, List<InvoiceItemDetailDto> items, BigDecimal total, String status, LocalDateTime createdAt) {
    public InvoiceDetailDto(Invoice invoice) {
        this(
                invoice.getId(),
                invoice.getUser().getName(),
                invoice.getItens().stream().map(InvoiceItemDetailDto::new).toList(),
                invoice.getTotal(),
                invoice.getInvoiceStatus().name(),
                invoice.getCreatedAt()
        );
    }
}
