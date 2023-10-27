package com.luizvictor.course.services;

import com.luizvictor.course.entities.User;
import com.luizvictor.course.exceptions.DatabaseException;
import com.luizvictor.course.exceptions.NotFountException;
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

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFountException("User not found"));
    }

    public User update(Long id, User userUpdate) {
        try {
            User user = userRepository.getReferenceById(id);
            user.update(userUpdate);
            return userRepository.save(user);
        }  catch (EntityNotFoundException e) {
            throw new NotFountException("User not found");
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
