package com.luizvictor.store.entities.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRoleDto(@NotBlank String role) {
}
