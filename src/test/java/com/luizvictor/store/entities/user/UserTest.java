package com.luizvictor.store.entities.user;

import com.luizvictor.store.exceptions.InvalidEmailException;
import com.luizvictor.store.exceptions.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.luizvictor.store.common.UserConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    @DisplayName(value = "Must create user")
    void musCreateUser() {
        User user = new User(USER);

        boolean encryptedPassword = new BCryptPasswordEncoder().matches("123456", user.getPassword());

        assertEquals("John", user.getName());
        assertEquals("john@email.com", user.getEmail());
        assertTrue(encryptedPassword);
        assertEquals("CUSTOMER", user.getRole().name());
    }

    @Test
    @DisplayName(value = "Must not create user with invalid email")
    void mustNotCreateUserWithInvalidEmail() {
        Exception exception = assertThrows(InvalidEmailException.class, () -> {
            new User(INVALID_EMAIL);
        });

        String expected = "Invalid email";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must not create user with password shorter than 6 characters")
    void mustNotCreateUserWithPasswordShorterThan6Characters() {
        Exception exception = assertThrows(InvalidPasswordException.class, () -> {
            new User(INVALID_PASSWORD);
        });

        String expected = "Password must be longer than 6 characters";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must change user role")
    void mustChangeUserRole() {
        User user = new User(USER);

        user.changeRole("admin");

        assertEquals("ADMIN", user.getRole().name());
    }

    @Test
    @DisplayName(value = "Must update user")
    void mustUpdateUser() {
        User user = new User(USER);

        user.update(USER_UPDATE);

        boolean encryptedPassword = new BCryptPasswordEncoder().matches("654321", user.getPassword());

        assertEquals("Joanna Doe", user.getName());
        assertEquals("joanna@email.com", user.getEmail());
        assertTrue(encryptedPassword);
        assertEquals("CUSTOMER", user.getRole().name());
    }

    @Test
    @DisplayName(value = "Must not update user with invalid email")
    void mustNotUpdateUserWithInvalidEmail() {
        Exception exception = assertThrows(InvalidEmailException.class, () -> {
            User user = new User(USER);
            user.update(INVALID_EMAIL);
        });

        String expected = "Invalid email";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must not update user with password shorter than 6 characters")
    void mustNotUpdateUserWithPasswordShorterThan6Characters() {
        Exception exception = assertThrows(InvalidPasswordException.class, () -> {
            User user = new User(USER);
            user.update(INVALID_PASSWORD);
        });

        String expected = "Password must be longer than 6 characters";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }
}