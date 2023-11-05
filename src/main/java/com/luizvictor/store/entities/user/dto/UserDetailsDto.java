package com.luizvictor.store.entities.user.dto;

import com.luizvictor.store.entities.user.User;

public record UserDetailsDto(Long id, String name, String email, String phone, String password, String role) {
    public UserDetailsDto(User user) {
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
