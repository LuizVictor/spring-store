package com.luizvictor.store.services;

import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.exceptions.UnauthorizedException;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authService;
    @Mock
    private UserRepository userRepository;

    private static Authentication authentication;
    private static final String VALID_EMAIL = "email@email.com";
    private static final String UNAUTHORIZED_EMAIL = "unauthorized@email.com";

    @BeforeAll
    static void beforeAll() {
        authentication = mock(Authentication.class);
        UserDetailsAuth userDetailsAuth = mock(UserDetailsAuth.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(userDetailsAuth);
        when(userDetailsAuth.getUsername()).thenReturn(VALID_EMAIL);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    @DisplayName("Must return user details when searched by user email")
    void loadUsersByUsername_validEmail_mustReturnUserDetails() {
        User user = mock(User.class);

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        UserDetails details = authService.loadUserByUsername(VALID_EMAIL);

        assertNotNull(details);
        assertEquals(user.getEmail(), details.getUsername());
    }

    @Test
    @DisplayName("Must throw NotFoundException when user is not found")
    void loadUsersByUsername_invalidEmail_mustThrowNotFoundException() {
        when(userRepository.findByEmail(UNAUTHORIZED_EMAIL)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> authService.loadUserByUsername(UNAUTHORIZED_EMAIL));
    }

    @Test
    @DisplayName("authorizedUser must not throw exception if user role is admin")
    void authorizedUser_mustNotThrowExceptionIfUserRoleIsAdmin() {
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        doReturn(authorities).when(authentication).getAuthorities();

        assertDoesNotThrow(() -> authService.authorize(UNAUTHORIZED_EMAIL));
    }


    @Test
    @DisplayName("authorizedUser must not throw exception if user email is authorized")
    void authorizedUser_mustNotThrowExceptionIfUserEmailIsAuthorized() {
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_CUSTOMER")
        );

        doReturn(authorities).when(authentication).getAuthorities();

        assertDoesNotThrow(() -> authService.authorize(VALID_EMAIL));
    }

    @Test
    @DisplayName("authorizedUser user must throw UnauthorizedException if user email is unauthorized and role is not admin")
    void authorizedUser_mustThrowUnauthorizedExceptionIfUserEmailIsUnauthorizedAndRoleIsNotAdmin() {
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_CUSTOMER")
        );

        doReturn(authorities).when(authentication).getAuthorities();

        assertThrows(UnauthorizedException.class, () -> authService.authorize(UNAUTHORIZED_EMAIL));
    }
}