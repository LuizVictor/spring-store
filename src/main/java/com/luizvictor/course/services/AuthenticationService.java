package com.luizvictor.course.services;

import com.luizvictor.course.entities.user.User;
import com.luizvictor.course.exceptions.UnauthorizedException;
import com.luizvictor.course.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public void authUser(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = userRepository.getReferenceById(id).getEmail();
        String authEmail = authEmail(auth);
        String authRole = authRole(auth);

        if (!Objects.equals(userEmail, authEmail) && !authRole.equals("ADMIN")) {
            throw new UnauthorizedException("Unauthorized access");
        }
    }

    private String authEmail(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return user.getEmail();
    }

    private String authRole(Authentication auth) {
        String role = auth.getAuthorities().toString();
        return role.replaceAll("\\[ROLE_", "").replaceAll("]", "");
    }
}
