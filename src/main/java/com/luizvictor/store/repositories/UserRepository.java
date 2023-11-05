package com.luizvictor.store.repositories;

import com.luizvictor.store.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     User findByEmail(String email);
}
