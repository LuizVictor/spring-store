package com.luizvictor.store.services;

import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.entities.user.dto.UserDetailsDto;
import com.luizvictor.store.entities.user.dto.UserDto;
import com.luizvictor.store.exceptions.DatabaseException;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDetailsDto> findAll() {
        return userRepository.findAll().stream().map(UserDetailsDto::new).toList();
    }

    public UserDetailsDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserDetailsDto(user);
    }

    public UserDetailsDto save(UserDto dto) {
        User user = new User(dto);
        return new UserDetailsDto(userRepository.save(user));
    }

    public UserDetailsDto changeRole(Long id, String role) {
        try {
            User user = userRepository.getReferenceById(id);
            user.changeRole(role);
            return new UserDetailsDto(userRepository.save(user));
        } catch (NullPointerException e) {
            throw new NotFoundException("User not found");
        }
    }

    public UserDetailsDto update(Long id, UserDto dto) {
        try {
            User user = userRepository.getReferenceById(id);
            user.update(dto);
            return new UserDetailsDto(userRepository.save(user));
        } catch (NullPointerException e) {
            throw new NotFoundException("User not found");
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
