package com.luizvictor.store.services;

import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.entities.user.dto.UpdateUserRoleDto;
import com.luizvictor.store.entities.user.dto.UserDetailsDto;
import com.luizvictor.store.entities.user.dto.UserDto;
import com.luizvictor.store.exceptions.DatabaseException;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authService;

    public UserService(UserRepository userRepository, AuthenticationService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public List<UserDetailsDto> findAll() {
        return userRepository.findAll().stream().map(UserDetailsDto::new).toList();
    }

    public UserDetailsDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        authService.authorizedUser(id);
        return new UserDetailsDto(user);
    }

    public UserDetailsDto save(UserDto dto) {
        User user = new User(dto);
        return new UserDetailsDto(userRepository.save(user));
    }

    public UserDetailsDto changeRole(Long id, UpdateUserRoleDto dto) {
        try {
            User user = userRepository.getReferenceById(id);
            user.changeRole(dto);
            return new UserDetailsDto(userRepository.save(user));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("User not found");
        }
    }

    public UserDetailsDto update(Long id, UserDto dto) {
        try {
            authService.authorizedUser(id);
            User user = userRepository.getReferenceById(id);
            user.update(dto);
            return new UserDetailsDto(userRepository.save(user));
        }  catch (EntityNotFoundException e) {
            throw new NotFoundException("User not found");
        }
    }

    public void delete(Long id) {
        try {
            authService.authorizedUser(id);
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
