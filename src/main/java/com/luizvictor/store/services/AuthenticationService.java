package com.luizvictor.store.services;

import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.exceptions.UnauthorizedException;
import com.luizvictor.store.repositories.UserRepository;
import com.luizvictor.store.security.UserDetailsAuth;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        return new UserDetailsAuth(user);
    }

    public void authorizedUser(String userEmail) {
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
