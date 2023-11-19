package com.luizvictor.store.services;

import com.luizvictor.store.entities.user.Role;
import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.entities.user.dto.UserDetailsDto;
import com.luizvictor.store.exceptions.InvalidEmailException;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.luizvictor.store.common.UserConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName(value = "Must save valid user")
    void save_withValidUser_mustReturnUserDetailsDto() {
        User user = new User(USER);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDetailsDto details = userService.save(USER);

        assertEquals("John", details.name());
        assertEquals("john@email.com", details.email());
        assertEquals("CUSTOMER", details.role());
        assertTrue(BCrypt.checkpw("123456", user.getPassword()));
    }

    @Test
    @DisplayName(value = "Must not save user with invalid email")
    void save_withInvalidUserEmail_mustThrowInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () -> userService.save(INVALID_EMAIL));
    }

    @Test
    @DisplayName(value = "Must find all users saved")
    void findAll_withSavedData_mustReturnListOfUserDetailsDto() {
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(user1.getRole()).thenReturn(Role.CUSTOMER);
        when(user2.getRole()).thenReturn(Role.CUSTOMER);

        List<UserDetailsDto> users = userService.findAll();

        assertEquals(2, users.size());
    }

    @Test
    @DisplayName(value = "Must throw NotFoundException if repository is empty")
    void findAll_withEmptyData_mustThrowNotFoundException() {
        when(userRepository.findAll()).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.findAll());
    }

    @Test
    @DisplayName(value = "Must find user by email")
    void findByIdEmail_withExistingEmail_mustReturnUserDetailDto() {
        User user = new User(USER);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserDetailsDto details = userService.findByEmail("john@email.com");

        assertNotNull(details);
        assertEquals("John", details.name());
        assertEquals("john@email.com", details.email());
        assertEquals("CUSTOMER", details.role());
        assertTrue(BCrypt.checkpw("123456", user.getPassword()));
    }

    @Test
    @DisplayName(value = "Must throw NotFoundException if user email not exist")
    void findById_withNonExistingId_mustThrowNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findByEmail("john@email.com"));
    }

    @Test
    @DisplayName(value = "Must change user role from CUSTOMER to ADMIN")
    void changeRole_withExistingUser_mustReturnUserDetailsDtoWithRoleAdmin() {
        User user = new User(USER);

        when(userRepository.getReferenceById(anyLong())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDetailsDto details = userService.changeRole(1L, "admin");

        assertEquals("ADMIN", details.role());
    }

    @Test
    @DisplayName(value = "Must throw NotFoundException when trying to changeRole ofd user with no existing ID")
    void changeRole_withNonExistingUser_mustThrowNotFoundException() {
        when(userRepository.getReferenceById(anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.changeRole(1L, "admin"));
    }

    @Test
    @DisplayName(value = "Must update user")
    void update_withExistingUser_mustReturnUserDetailsDtoWithNameJoana() {
        User user = new User(USER);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDetailsDto details = userService.update("john@email.com", USER_UPDATE);

        assertEquals("Joanna Doe", details.name());
        assertEquals("joanna@email.com", details.email());
        assertEquals("CUSTOMER", details.role());
        assertTrue(BCrypt.checkpw("654321", user.getPassword()));
    }

    @Test
    @DisplayName(value = "Must throw NotFoundException when trying to update user with no existing email")
    void update_withNonExistingUser_mustThrowNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update("john@email.com", USER_UPDATE));
    }

    @Test
    @DisplayName(value = "Must delete user")
    void delete_existingUser_mustDeleteUser() {
        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}