package com.luizvictor.store.entities.order.dto;

import com.luizvictor.store.entities.orderItem.OrderItemDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDto(
        @NotNull
        @Min(value = 1)
        Long userId,
        @NotEmpty
        List<OrderItemDto> items) {
}
