package com.luizvictor.store.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.luizvictor.store.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${security.token.secret}")
    private String secret;

    public String create(UserDetailsAuth user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .create()
                    .withIssuer("Store spring")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error on generate jwt token ", exception);
        }
    }

    public String verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("Store spring").build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            throw new UnauthorizedException("Unauthorized access");
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
