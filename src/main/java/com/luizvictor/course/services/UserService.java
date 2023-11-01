package com.luizvictor.course.services;

import com.luizvictor.course.entities.user.User;
import com.luizvictor.course.entities.user.dto.UserDetailDto;
import com.luizvictor.course.entities.user.dto.UserDto;
import com.luizvictor.course.entities.user.dto.UserUpdateDto;
import com.luizvictor.course.exceptions.DatabaseException;
import com.luizvictor.course.exceptions.NotFoundException;
import com.luizvictor.course.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDetailDto> findAll() {
        return userRepository.findAll().stream().map(UserDetailDto::new).toList();
    }

    public UserDetailDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserDetailDto(user);
    }

    public UserDetailDto save(UserDto userDto) {
        User user = new User(userDto);
        return new UserDetailDto(userRepository.save(user));
    }

    public UserDetailDto update(Long id, UserUpdateDto userUpdate) {
        try {
            User user = userRepository.getReferenceById(id);
            user.update(userUpdate);
            return new UserDetailDto(userRepository.save(user));
        }  catch (EntityNotFoundException e) {
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
