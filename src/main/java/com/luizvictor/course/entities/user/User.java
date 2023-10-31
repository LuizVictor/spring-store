package com.luizvictor.course.entities.user;

import com.luizvictor.course.entities.user.dto.UserDto;
import com.luizvictor.course.entities.user.dto.UserUpdateDto;
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
    private String email;
    private String phone;
    private String password;

    public User(UserDto userDto) {
        this.name = userDto.name();
        this.email = userDto.email();
        this.phone = userDto.phone();
        this.password = userDto.password();
    }

    public void update(UserUpdateDto userDetailDto) {
        this.name = userDetailDto.name();
        this.email = userDetailDto.email();
        this.phone = userDetailDto.phone();
        this.password = userDetailDto.password();
    }
}
