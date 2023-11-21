package com.luizvictor.store.entities.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PaymentDto(
        @NotNull
        String transaction,
        @NotNull
        @Min(value = 1)
        Long orderId) {
}
