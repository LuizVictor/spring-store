package com.luizvictor.store.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizvictor.store.entities.Category;
import com.luizvictor.store.repositories.CategoryRepository;
import com.luizvictor.store.repositories.OrderRepository;
import com.luizvictor.store.services.OrderService;
import com.luizvictor.store.services.ProductService;
import com.luizvictor.store.services.UserService;
import jakarta.transaction.Transactional;
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

import static com.luizvictor.store.common.OrderConstants.*;
import static com.luizvictor.store.common.ProductConstants.PRODUCT;
import static com.luizvictor.store.common.UserConstants.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "john@email.com", password = "123456", authorities = "ROLE_ADMIN")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderResourceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        userService.save(USER);
        Category category = new Category("category");
        categoryRepository.save(category);
        productService.save(PRODUCT);
        orderService.save(ORDER);
    }

    @Test
    @DisplayName(value = "Must save order")
    void save_withValidData_mustReturnStatusCreated() throws Exception {
        mvc.perform(post("/orders")
                        .content(mapper.writeValueAsBytes(ORDER))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.user").value("John"))
                .andExpect(jsonPath("$.status").value("PAID"));

        assertEquals(2, orderRepository.count());
    }

    @Test
    @DisplayName(value = "Must not save order with invalid data and must return status 400")
    void save_withInvalidData_mustReturnStatusBadRequest() throws Exception {
        mvc.perform(post("/orders")
                        .content(mapper.writeValueAsBytes(EMPTY_ORDER))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertEquals(1, orderRepository.count());
    }

    @Test
    @DisplayName(value = "Must find all orders saved")
    void findAll_withSavedData_mustReturnStatusOk() throws Exception {
        mvc.perform(get("/orders").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        assertEquals(1, orderRepository.count());
    }

    @Test
    @DisplayName(value = "Must find order with valid user email")
    void findByIdUserEmail_withExistingId_mustReturnStatusOk() throws Exception {
        mvc.perform(get("/orders/my-orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName(value = "Must return 404 when search orders with no existing email")
    @WithMockUser(username = "email@email.com", password = "123456", authorities = "ROLE_ADMIN")
    void findById_withNonExistingId_mustReturnStatusNotFound() throws Exception {
        mvc.perform(get("/orders/my-orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Must update order status and return 200")
    void updateStatus_withValidData_mustReturnOk() throws Exception {
        mvc.perform(patch("/orders/{id}", 1)
                        .content(mapper.writeValueAsBytes(ORDER_STATUS_UPDATE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DELIVERED"));
    }

    @Test
    @DisplayName(value = "Must not update order status with invalid data and must return status 400")
    void updateStatus_withInvalidData_mustReturnStatusBadRequest() throws Exception {
        mvc.perform(post("/orders")
                        .content(mapper.writeValueAsBytes(INVALID_ORDER_STATUS_UPDATE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}