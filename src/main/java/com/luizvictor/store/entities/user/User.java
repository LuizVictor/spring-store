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
    @Embedded
    private Email email;
    private String phone;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(UserDto dto) {
        this.name = dto.name();
        this.email = new Email(dto.email());
        this.phone = dto.phone();
        this.password = createPasswordEncrypted(dto.password());
        this.role = Role.CUSTOMER;
    }

    private String createPasswordEncrypted(String password) {
        return new BCryptPasswordEncoder().encode(new Password(password).toString());
    }

    public String getEmail() {
        return email.toString();
    }

    public void changeRole(UpdateUserRoleDto dto) {
        this.role = Role.valueOf(dto.role().toUpperCase());
    }

    public void update(UserDto dto) {
        this.name = dto.name();
        this.email = new Email(dto.email());
        this.phone = dto.phone();
        this.password = createPasswordEncrypted(dto.password());
    }
}
