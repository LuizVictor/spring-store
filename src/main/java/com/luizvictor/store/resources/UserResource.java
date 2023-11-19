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
import java.security.Principal;
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
        System.out.println("List of users: " + users);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/my-account")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserDetailsDto> findByEmail(Principal principal) {
        UserDetailsDto user = userService.findByEmail(principal.getName());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserDetailsDto> save(@RequestBody @Valid UserDto dto, UriComponentsBuilder uriBuilder) {
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

    @PutMapping("/my-account")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserDetailsDto> update(Principal principal, @RequestBody @Valid UserDto dto) {
        UserDetailsDto user = userService.update(principal.getName(), dto);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
