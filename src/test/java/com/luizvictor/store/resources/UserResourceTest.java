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
@WithMockUser(authorities = "ROLE_ADMIN")
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
    @DisplayName(value = "Must create user")
    void create_withValidData_mustReturnStatusCreated() throws Exception {
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(USER_UPDATE)))
                .andExpect(status().isCreated());

        assertEquals(2, userRepository.count());
    }

    @Test
    @DisplayName(value = "Must not create user with invalid data and must return status 400")
    void create_withInvalidData_mustReturnStatusBadRequest() throws Exception {
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(INVALID_EMAIL)))
                .andExpect(status().isBadRequest());

        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName(value = "Must not create user with repeated email and must return status 422")
    void create_withEmailRepeated_mustReturnStatusUnprocessableEntity() throws Exception {
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(USER)))
                .andExpect(status().isUnprocessableEntity());

        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName(value = "Must find all users saved")
    void findAll_mustReturnListOfUserDetailsDto() throws Exception {
        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName(value = "Must find user with valid id")
    void findById_withExistingId_mustReturnStatusOk() throws Exception {
        mvc.perform(get("/users/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    @DisplayName(value = "Must return 404 when search user with no existing id")
    void findById_withNonExistingId_mustReturnStatusNotFound() throws Exception {
        mvc.perform(get("/users/{id}", 2))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Must update user")
    void update_withValidData_mustUpdateUser() throws Exception {
        mvc.perform(put("/users/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(USER_UPDATE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Joanna Doe"))
                .andExpect(jsonPath("$.email").value("joanna@email.com"));
    }

    @Test
    @DisplayName(value = "Must change user role from CONSUMER to ADMIN")
    void changeRole_withValidDate_mustChangeUserRoleToAdmin() throws Exception {
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