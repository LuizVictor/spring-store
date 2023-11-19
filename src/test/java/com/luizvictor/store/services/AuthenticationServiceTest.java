package com.luizvictor.store.services;

import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.repositories.UserRepository;
import com.luizvictor.store.security.UserDetailsAuth;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.luizvictor.store.common.UserConstants.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authService;
    @Mock
    private UserRepository userRepository;
    private static final String VALID_EMAIL = "email@email.com";
    private static final String UNAUTHORIZED_EMAIL = "unauthorized@email.com";

    @BeforeAll
    static void beforeAll() {
        Authentication authentication = mock(Authentication.class);
        UserDetailsAuth userDetailsAuth = mock(UserDetailsAuth.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(userDetailsAuth);
        when(userDetailsAuth.getUsername()).thenReturn(VALID_EMAIL);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    @DisplayName("Must return user details when searched by user email")
    void loadUsersByUsername_withValidEmail_mustReturnUserDetails() {
        User user = new User(USER);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        UserDetails details = authService.loadUserByUsername(VALID_EMAIL);

        assertNotNull(details);
        assertEquals("john@email.com", details.getUsername());
    }

    @Test
    @DisplayName("Must throw NotFoundException when user is not found")
    void loadUsersByUsername_withInvalidEmail_mustThrowNotFoundException() {
        when(userRepository.findByEmail(UNAUTHORIZED_EMAIL)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> authService.loadUserByUsername(UNAUTHORIZED_EMAIL));
    }
}