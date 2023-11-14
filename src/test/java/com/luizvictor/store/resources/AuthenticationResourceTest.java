package com.luizvictor.store.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.entities.user.dto.UserLoginDto;
import com.luizvictor.store.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.luizvictor.store.common.UserConstants.USER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        var user = new User(USER);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "Must return valid JWT token when user is valid")
    public void login_withValidData_returnJwtToken() throws Exception {
        UserLoginDto userLoginDto = new UserLoginDto("john@email.com", "123456");
        String requestBody = objectMapper.writeValueAsString(userLoginDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists()).andReturn();
    }

    @Test
    @DisplayName(value = "Must return 404 when user is invalid")
    public void login_withInvalidData_returnNotFound() throws Exception {
        UserLoginDto userLoginDto = new UserLoginDto("email@email.com", "123456");
        String requestBody = objectMapper.writeValueAsString(userLoginDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }
}