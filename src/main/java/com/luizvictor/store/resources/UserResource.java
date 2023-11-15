package com.luizvictor.store.resources;

import com.luizvictor.store.entities.user.dto.UpdateUserRoleDto;
import com.luizvictor.store.entities.user.dto.UserDetailsDto;
import com.luizvictor.store.entities.user.dto.UserDto;
import com.luizvictor.store.exceptions.UnprocessableEntityException;
import com.luizvictor.store.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<UserDetailsDto>> findAll() {
        List<UserDetailsDto> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserDetailsDto> findById(@PathVariable Long id) {
        UserDetailsDto user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserDetailsDto> create(@RequestBody @Valid UserDto dto, UriComponentsBuilder uriBuilder) {
        try {
            UserDetailsDto user = userService.save(dto);
            URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.id()).toUri();
            return ResponseEntity.created(uri).body(user);
        } catch (DataIntegrityViolationException  e) {
            throw new UnprocessableEntityException("User already registered");
        }
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserDetailsDto> changeRole(@PathVariable Long id, @RequestBody @Valid UpdateUserRoleDto dto) {
        UserDetailsDto user = userService.changeRole(id, dto.role());
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserDetailsDto> update(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        UserDetailsDto user = userService.update(id, dto);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
