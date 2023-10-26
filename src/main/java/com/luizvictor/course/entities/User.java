package com.luizvictor.course.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;

    public void update(User userUpdate) {
        this.name = userUpdate.getName();
        this.email = userUpdate.getEmail();
        this.phone = userUpdate.getPhone();
        this.password = userUpdate.getPassword();
    }
}
