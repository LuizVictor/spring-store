package com.luizvictor.course.config;

import com.luizvictor.course.entities.User;
import com.luizvictor.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        User johnDoe = new User(null, "John Doe", "john@email.com", "1111-1111", "123");
        User joannaDoe = new User(null, "Joanna Doe", "joanna@email.com", "1111-1111", "123");
        userRepository.saveAll(Arrays.asList(johnDoe, joannaDoe));
    }
}
