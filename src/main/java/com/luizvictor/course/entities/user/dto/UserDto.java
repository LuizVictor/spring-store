package com.luizvictor.course.entities.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        String phone,
        @Size(min = 6)
        String password
) {
}
