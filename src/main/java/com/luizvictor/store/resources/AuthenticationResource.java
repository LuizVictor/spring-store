package com.luizvictor.store.resources;

import com.luizvictor.store.entities.user.dto.UserLoginDetailsDto;
import com.luizvictor.store.entities.user.dto.UserLoginDto;
import com.luizvictor.store.security.TokenService;
import com.luizvictor.store.security.UserDetailsAuth;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationResource {
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AuthenticationResource(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<UserLoginDetailsDto> login(@RequestBody @Valid UserLoginDto dto) {
        var token = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication auth = manager.authenticate(token);
        String tokenJWT = tokenService.create((UserDetailsAuth) auth.getPrincipal());
        return ResponseEntity.ok().body(new UserLoginDetailsDto(tokenJWT));
    }
}
