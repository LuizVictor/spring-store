package com.luizvictor.store.common;

import com.luizvictor.store.entities.user.dto.UserDto;

public class UserConstants {
    public static UserDto USER = new UserDto("John", "john@email.com", "111-111", "123456");
    public static UserDto USER_UPDATE = new UserDto("Joanna Doe", "joanna@email.com", "111-111", "654321");
    public static UserDto INVALID_EMAIL = new UserDto("Joanna", "email", "111", "123456");
    public static UserDto INVALID_PASSWORD = new UserDto("Joanna", "email@email.com", "111", "123");
}
