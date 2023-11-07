package com.luizvictor.store.entities.user;

import com.luizvictor.store.entities.user.dto.UpdateUserRoleDto;
import com.luizvictor.store.entities.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        this.email = createEmail(dto.email());
        this.phone = dto.phone();
        this.password = createPassword(dto.password());
        this.role = Role.CUSTOMER;
    }

    private String createEmail(String email) {
        Email email1 = new Email(email);
        return email1.toString();
    }

    private String createPassword(String password) {
        Password password1 = new Password(password);
        return password1.toString();
    }

    public void changeRole(UpdateUserRoleDto dto) {
        this.role = Role.valueOf(dto.role().toUpperCase());
    }

    public void update(UserDto dto) {
        this.name = dto.name();
        this.email = createEmail(dto.email());
        this.phone = dto.phone();
        this.password = createPassword(dto.password());
    }
}
