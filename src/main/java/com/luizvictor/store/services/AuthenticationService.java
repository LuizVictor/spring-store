package com.luizvictor.store.services;

import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.exceptions.UnauthorizedException;
import com.luizvictor.store.repositories.UserRepository;
import com.luizvictor.store.security.TokenService;
import com.luizvictor.store.security.UserDetailsAuth;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthenticationManager manager;
    private final TokenService tokenService;


    public AuthenticationService(UserRepository userRepository, @Lazy AuthenticationManager manager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            User user = userRepository.findByEmail(email).orElse(null);
            return new UserDetailsAuth(user);
        } catch (NullPointerException e) {
            throw new NotFoundException("User not found");
        }
    }

    public String authenticate(String email, String password) {
        var token = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = manager.authenticate(token);
        return tokenService.create((UserDetailsAuth) auth.getPrincipal());
    }

    public void authorize(String userEmail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = authEmail(auth);
        String authRole = authRole(auth);

        if (!Objects.equals(userEmail, authEmail) && !authRole.equals("ADMIN")) {
            throw new UnauthorizedException("Unauthorized access");
        }
    }

    private String authEmail(Authentication auth) {
        UserDetailsAuth user = (UserDetailsAuth) auth.getPrincipal();
        return user.getUsername();
    }

    private String authRole(Authentication auth) {
        String role = auth.getAuthorities().toString();
        return role.replaceAll("\\[ROLE_", "").replaceAll("]", "");
    }
}
