package com.luizvictor.course.entities.user.dto;

import com.luizvictor.course.entities.user.User;

public record UserDetailDto(Long id, String name, String email, String phone, String password, String role) {
    public UserDetailDto(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getPassword(),
                user.getRole().name()
        );
    }
}
