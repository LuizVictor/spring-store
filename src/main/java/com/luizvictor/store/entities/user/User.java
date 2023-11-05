package com.luizvictor.store.entities.user;

import com.luizvictor.store.entities.user.dto.UpdateUserRoleDto;
import com.luizvictor.store.entities.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phone;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(UserDto dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.phone = dto.phone();
        this.password = new BCryptPasswordEncoder().encode(dto.password());
        this.role = Role.CUSTOMER;
    }

    public void changeRole(UpdateUserRoleDto dto) {
        this.role = Role.valueOf(dto.role().toUpperCase());
    }

    public void update(UserDto dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.phone = dto.phone();
        this.password = new BCryptPasswordEncoder().encode(dto.password());
    }
}
