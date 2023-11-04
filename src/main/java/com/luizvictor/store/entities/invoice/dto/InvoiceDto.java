package com.luizvictor.store.entities.invoice.dto;

import com.luizvictor.store.entities.orderItem.InvoiceItemDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record InvoiceDto(
        @NotNull
        @Min(value = 1)
        Long userId,
        @NotEmpty
        List<InvoiceItemDto> items) {
}
