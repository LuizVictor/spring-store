package com.luizvictor.store.entities.user;

import com.luizvictor.store.exceptions.InvalidPasswordException;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
public class Password {
    private String password;

    public Password(String password) {
        this.validate(password);
    }

    private void validate(String password) {
        String regex = "^.{6,}$";
        boolean isValid = Pattern.compile(regex).matcher(password).find();

        if (!isValid) {
            throw new InvalidPasswordException("Password must be longer than 6 characters");
        }

        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public String toString() {
        return password;
    }
}
