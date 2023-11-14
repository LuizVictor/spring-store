package com.luizvictor.store.resources;

import com.luizvictor.store.entities.user.dto.UserLoginDetailsDto;
import com.luizvictor.store.entities.user.dto.UserLoginDto;
import com.luizvictor.store.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Auth")
public class AuthenticationResource {
    private final AuthenticationService authService;

    public AuthenticationResource(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<UserLoginDetailsDto> login(@RequestBody @Valid UserLoginDto dto) {
        String token = authService.authenticate(dto.email(), dto.password());

        return ResponseEntity.ok().body(new UserLoginDetailsDto(token));
    }
}
