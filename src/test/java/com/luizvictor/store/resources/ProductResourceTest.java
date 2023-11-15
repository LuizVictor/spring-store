package com.luizvictor.store.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizvictor.store.entities.Category;
import com.luizvictor.store.repositories.CategoryRepository;
import com.luizvictor.store.repositories.ProductRepository;
import com.luizvictor.store.services.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.luizvictor.store.common.ProductConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(authorities = "ROLE_ADMIN")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductResourceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        Category category = new Category("category");
        categoryRepository.save(category);
        productService.save(PRODUCT);
    }

    @Test
    @DisplayName(value = "Must find all products saved")
    void findAll_mustReturnAllProductsSaved() throws Exception {
        mvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        assertEquals(1, productRepository.count());
    }

    @Test
    @DisplayName(value = "Must find product with valid id")
    void findById_withExistingId_mustReturnStatusOk() throws Exception {
        mvc.perform(get("/products/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product"))
                .andExpect(jsonPath("$.category").value("category"));
    }

    @Test
    @DisplayName(value = "Must return 404 when search product with no existing id")
    void findById_withNonExistingId_mustReturnStatusNotFound() throws Exception {
        mvc.perform(get("/products/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "Must save product")
    void save_withValidData_mustReturnStatusCreated() throws Exception {
        mvc.perform(post("/products")
                        .content(mapper.writeValueAsBytes(PRODUCT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Product"))
                .andExpect(jsonPath("$.description").value("test product"))
                .andExpect(jsonPath("$.price").value("100"));

        assertEquals(2, productRepository.count());
    }

    @Test
    @DisplayName(value = "Must not save product with invalid data and must return status 400")
    void save_withInvalidData_mustReturnStatusBadRequest() throws Exception {
        mvc.perform(post("/products")
                        .content(mapper.writeValueAsBytes(INVALID_NAME_PRODUCT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertEquals(1, productRepository.count());
    }
}