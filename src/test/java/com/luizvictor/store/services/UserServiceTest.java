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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Mock
    private AuthenticationService authService;

    @Test
    @DisplayName(value = "Must save user")
    void save_validUser_mustReturnUserDetailsDto() {
        User user = new User(USER);

        when(userRepository.save(any())).thenReturn(user);

        UserDetailsDto details = userService.save(USER);

        boolean encryptedPassword = new BCryptPasswordEncoder().matches("123456", user.getPassword());

        assertEquals("John", details.name());
        assertEquals("john@email.com", details.email());
        assertEquals("CUSTOMER", details.role());
        assertTrue(encryptedPassword);
    }

    @Test
    @DisplayName(value = "Must not save user with invalid email")
    void save_invalidUserEmail_mustThrowException() {
        assertThrows(InvalidEmailException.class, () -> userService.save(INVALID_EMAIL));
    }

    @Test
    @DisplayName(value = "Must find all users saved")
    void findAll_mustReturnListOfUserDetailsDto() {
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(user1.getRole()).thenReturn(Role.CUSTOMER);
        when(user2.getRole()).thenReturn(Role.CUSTOMER);
        List<UserDetailsDto> users = userService.findAll();

        assertEquals(2, users.size());
    }

    @Test
    @DisplayName(value = "Must find user by id")
    void findById_existingId_mustReturnUserDetailDto() {
        User user = new User(USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDetailsDto details = userService.findById(1L);

        assertNotNull(details);
        assertEquals("John", details.name());
    }

    @Test
    @DisplayName(value = "Must throw NotFoundException if user id not exist")
    void findById_noExistingId_mustThrowNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    @DisplayName(value = "Must change user role from CUSTOMER to ADMIN")
    void changeRole_existingId_mustReturnUserDetailsDtoWithRoleAdmin() {
        User user = new User(USER);

        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDetailsDto details = userService.changeRole(1L, "admin");

        assertEquals("ADMIN", details.role());
    }

    @Test
    @DisplayName(value = "Must update user")
    void update_existingId_mustReturnUserDetailsDtoWithNameJoana() {
        User user = new User(USER);

        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);

        UserDetailsDto details = userService.update(1L, USER_UPDATE);

        assertEquals("Joanna Doe", details.name());
        assertEquals("joanna@email.com", details.email());
    }

    @Test
    @DisplayName(value = "Must delete user")
    void delete_existingId_mustDeleteUser() {
        User user = mock(User.class);
        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(user.getEmail()).thenReturn("email@email.com");
        doNothing().when(authService).authorizedUser(anyString());

        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}