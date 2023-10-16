package com.luizvictor.course.resources;

import com.luizvictor.course.entities.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
    @GetMapping
    public ResponseEntity<User> findAll() {
        User user = new User(1L, "John Doe", "email@email.com", "111", "1234");
        return ResponseEntity.ok().body(user);
    }
}
