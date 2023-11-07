package com.luizvictor.store.entities.user;

import com.luizvictor.store.entities.user.dto.UpdateUserRoleDto;
import com.luizvictor.store.entities.user.dto.UserDto;
import com.luizvictor.store.exceptions.InvalidEmailException;
import com.luizvictor.store.exceptions.InvalidPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
    @Test
    @DisplayName(value = "Must create user")
    void musCreateUser() {
        UserDto userDto = new UserDto("John Doe", "john@email.com", "1111", "123456");
        User user = new User(userDto);

        Assertions.assertEquals("John Doe", user.getName());
        Assertions.assertEquals("john@email.com", user.getEmail());
        Assertions.assertEquals("CUSTOMER", user.getRole().name());
    }

    @Test
    @DisplayName(value = "Must not create user with invalid email")
    void mustNotCreateUserWithInvalidEmail() {
        Exception exception = Assertions.assertThrows(InvalidEmailException.class, () -> {
            UserDto userDto = new UserDto("John Doe", "invalid.email.com", "1111", "123");
            new User(userDto);
        });

        String expected = "Invalid email";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must not create user with password shorter than 6 characters")
    void mustNotCreateUserWithPasswordShorterThan6Characters() {
        Exception exception = Assertions.assertThrows(InvalidPasswordException.class, () -> {
            UserDto userDto = new UserDto("John Doe", "john@email.com", "1111", "123");
            new User(userDto);
        });

        String expected = "Password must be longer than 6 characters";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must change user role")
    void mustChangeUserRole() {
        UserDto userDto = new UserDto("John Doe", "john@email.com", "1111", "123456");
        User user = new User(userDto);

        UpdateUserRoleDto userRoleDto = new UpdateUserRoleDto("admin");

        user.changeRole(userRoleDto);

        Assertions.assertEquals("ADMIN", user.getRole().name());
    }

    @Test
    @DisplayName(value = "Must update user")
    void mustUpdateUser() {
        UserDto userDto = new UserDto("John Doe", "john@email.com", "1111", "123456");
        User user = new User(userDto);

        UserDto updateDto = new UserDto("Joanna Doe", "joanna@email.com", "2222", "654321");
        user.update(updateDto);

        Assertions.assertEquals("Joanna Doe", user.getName());
        Assertions.assertEquals("joanna@email.com", user.getEmail());
        Assertions.assertEquals("CUSTOMER", user.getRole().name());
    }

    @Test
    @DisplayName(value = "Must not update user with invalid email")
    void mustNotUpdateUserWithInvalidEmail() {
        Exception exception = Assertions.assertThrows(InvalidEmailException.class, () -> {
            UserDto userDto = new UserDto("John Doe", "john@email.com", "1111", "123456");
            User user = new User(userDto);

            UserDto updateDto = new UserDto("Joanna Doe", "joanna.email.com", "2222", "654321");
            user.update(updateDto);
        });

        String expected = "Invalid email";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must not update user with password shorter than 6 characters")
    void mustNotUpdateUserWithPasswordShorterThan6Characters() {
        Exception exception = Assertions.assertThrows(InvalidPasswordException.class, () -> {
            UserDto userDto = new UserDto("John Doe", "john@email.com", "1111", "123456");
            User user = new User(userDto);

            UserDto updateDto = new UserDto("Joanna Doe", "joanna@email.com", "2222", "654");
            user.update(updateDto);
        });

        String expected = "Password must be longer than 6 characters";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }
}