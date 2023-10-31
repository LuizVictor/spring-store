package com.luizvictor.course.entities.user.dto;

import com.luizvictor.course.entities.user.User;

public record UserUpdateDto(String name, String email, String phone, String password) {
    public UserUpdateDto(User user) {
        this(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getPassword()
        );
    }
}
