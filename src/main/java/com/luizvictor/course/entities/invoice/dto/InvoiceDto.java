package com.luizvictor.course.entities.invoice.dto;

import com.luizvictor.course.entities.invoice.InvoiceStatus;
import com.luizvictor.course.entities.orderItem.InvoiceItemDto;

import java.util.List;

public record InvoiceDto(Long userId, List<InvoiceItemDto> items) {
}
