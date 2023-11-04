package com.luizvictor.course.entities.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDto(
        @NotBlank
        String name,
        String description,
        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal price,
        @NotNull
        @Min(value = 1)
        Long category
) {
}
