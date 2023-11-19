package com.luizvictor.store.services;

import com.luizvictor.store.entities.Category;
import com.luizvictor.store.entities.product.Product;
import com.luizvictor.store.entities.product.dto.ProductDetailsDto;
import com.luizvictor.store.exceptions.EmptyNameException;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.repositories.CategoryRepository;
import com.luizvictor.store.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.luizvictor.store.common.ProductConstants.INVALID_NAME_PRODUCT;
import static com.luizvictor.store.common.ProductConstants.PRODUCT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;

    private static final Category CATEGORY = new Category("Category");

    @Test
    @DisplayName(value = "Must save product")
    void save_withValidProduct_mustReturnProductDetailsDto() {
        Product product = new Product(PRODUCT, CATEGORY);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(CATEGORY));
        when(productRepository.save(any())).thenReturn(product);

        ProductDetailsDto details = productService.save(PRODUCT);

        assertNotNull(details);
        assertEquals("Product", details.name());
        assertEquals("test product", details.description());
        assertEquals(BigDecimal.valueOf(100), details.price());
        assertEquals("Category", details.category());
    }

    @Test
    @DisplayName(value = "Must not save product with empty name")
    void save_withEmptyName_mustThrowEmptyNameException() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(CATEGORY));

        assertThrows(EmptyNameException.class, () -> productService.save(INVALID_NAME_PRODUCT));
    }

    @Test
    @DisplayName(value = "Must return list of products saved")
    void findAll_withSavedData_mustReturnListOfProductDetailsDto() {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(product1.getCategory()).thenReturn(CATEGORY);
        when(product2.getCategory()).thenReturn(CATEGORY);

        List<ProductDetailsDto> details = productService.findAll();

        assertNotNull(details);
        assertEquals(2, details.size());
    }

    @Test
    @DisplayName(value = "Must throw NotFoundException if repository is empty")
    void findAll_withEmptyData_mustThrowNotFoundException() {
        when(productRepository.findAll()).thenReturn(null);
        assertThrows(NotFoundException.class, () -> productService.findAll());
    }

    @Test
    @DisplayName(value = "Must find product by id")
    void findById_withExistingId_mustReturnProductDetailsDto() {
        Product product = new Product(PRODUCT, CATEGORY);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDetailsDto details = productService.findById(1L);

        assertNotNull(details);
        assertEquals("Product", details.name());
        assertEquals("test product", details.description());
        assertEquals(BigDecimal.valueOf(100), details.price());
        assertEquals("Category", details.category());
    }

    @Test
    @DisplayName(value = "Must throw not found exception with invalid id")
    void findById_withNonExistingId_mustThrowNotFoundException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.findById(1L));
    }
}