package com.luizvictor.store.entities.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
