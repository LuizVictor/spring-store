package com.luizvictor.store.resources;

import com.luizvictor.store.entities.user.dto.UpdateRoleDto;
import com.luizvictor.store.entities.user.dto.UserDetailDto;
import com.luizvictor.store.entities.user.dto.UserDto;
import com.luizvictor.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailDto>> findAll() {
        List<UserDetailDto> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDetailDto> findById(@PathVariable Long id) {
        UserDetailDto user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserDetailDto> create(@RequestBody @Valid UserDto dto, UriComponentsBuilder uriBuilder) {
        UserDetailDto user = userService.save(dto);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDetailDto> changeRole(@PathVariable Long id, @RequestBody @Valid UpdateRoleDto dto) {
        UserDetailDto user = userService.changeRole(id, dto);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailDto> update(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        UserDetailDto user = userService.update(id, dto);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
