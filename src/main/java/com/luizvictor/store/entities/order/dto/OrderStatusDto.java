package com.luizvictor.store.entities.order.dto;


import jakarta.validation.constraints.NotBlank;

public record OrderStatusDto(@NotBlank String status) {
}
