package com.luizvictor.store.entities.user;

import com.luizvictor.store.exceptions.InvalidEmailException;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
public class Email {
    private String email;

    public Email(String email) {
        this.validate(email);
    }

    private void validate(String email) {
        String regex = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
        boolean isValid = Pattern.compile(regex).matcher(email).find();

        if (!isValid) {
            throw new InvalidEmailException("Invalid email");
        }

        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}
