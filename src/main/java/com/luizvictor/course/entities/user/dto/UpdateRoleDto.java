package com.luizvictor.course.entities.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateRoleDto(@NotBlank String role) {
}
