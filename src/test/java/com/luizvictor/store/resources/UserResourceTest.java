package com.luizvictor.store.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizvictor.store.entities.user.dto.UpdateUserRoleDto;
import com.luizvictor.store.repositories.UserRepository;
import com.luizvictor.store.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.luizvictor.store.common.UserConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "john@email.com", password = "123456", authorities = "ROLE_ADMIN")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserResourceTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private final long ID = 1;

    @BeforeEach
    public void setUp() {
        userService.save(USER);
    }

    @Test
    @DisplayName(value = "Must save user")
    void save_withValidData_mustReturnStatusCreated() throws Exception {
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(USER_UPDATE)))
                .andExpect(status().isCreated());

        assertEquals(2, userRepository.count());
    }

    @Test
    @DisplayName(value = "Must not save user with invalid data and must return status 400")
    void save_withInvalidData_mustReturnStatusBadRequest() throws Exception {
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(INVALID_EMAIL)))
                .andExpect(status().isBadRequest());

        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName(value = "Must not save user with repeated email and must return status 422")
    void save_withEmailRepeated_mustReturnStatusUnprocessableEntity() throws Exception {
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(USER)))
                .andExpect(status().isUnprocessableEntity());

        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName(value = "Must find all users saved")
    void findAll_withSavedData_mustReturnStatusOk() throws Exception {
        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName(value = "Must find user with valid email")
    void findByEmail_withExistingEmail_mustReturnStatusOk() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        mvc.perform(get("/users/my-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@email.com"));
    }

    @Test
    @DisplayName(value = "Must return 404 when search user with no existing email")
    @WithMockUser(username = "joanna@email.com", password = "123456", authorities = "ROLE_ADMIN")
    void findById_withNonExistingId_mustReturnStatusNotFound() throws Exception {
        mvc.perform(get("/users/my-account"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Must update user")
    void update_withValidData_mustReturnOk() throws Exception {
        mvc.perform(put("/users/my-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(USER_UPDATE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Joanna Doe"))
                .andExpect(jsonPath("$.email").value("joanna@email.com"));
    }

    @Test
    @DisplayName(value = "Must return 404 when try update user with no existing email")
    @WithMockUser(username = "joanna@email.com", password = "123456", authorities = "ROLE_ADMIN")
    void update_withInvalidData_mustReturnBadRequest() throws Exception {
        mvc.perform(put("/users/my-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(INVALID_EMAIL)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = "Must change user role from CONSUMER to ADMIN")
    void changeRole_withValidData_mustChangeUserRoleToAdmin() throws Exception {
        UpdateUserRoleDto roleDto = new UpdateUserRoleDto("admin");
        mvc.perform(patch("/users/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(roleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void delete_mustDeleteUser() throws Exception {
        mvc.perform(delete("/users/{id}", ID)).andExpect(status().isNoContent());

        assertEquals(0, userRepository.count());
    }
}