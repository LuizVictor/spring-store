package com.luizvictor.store.entities.product;

import com.luizvictor.store.entities.Category;
import com.luizvictor.store.entities.product.dto.ProductDto;
import com.luizvictor.store.exceptions.EmptyNameException;
import com.luizvictor.store.exceptions.InvalidPriceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductTest {
    @Test
    @DisplayName(value = "Must create product")
    void mustCreateProduct() {
        ProductDto dto = new ProductDto("Product", "test product", new BigDecimal(100), 1L);
        Category category = new Category("Category");
        Product product = new Product(dto, category);

        Assertions.assertEquals("Product", product.getName());
        Assertions.assertEquals("test product", product.getDescription());
        Assertions.assertEquals(new BigDecimal(100), product.getPrice());
    }

    @Test
    @DisplayName(value = "Must not create product with empty name")
    void mustNotCreateProductWithEmptyName() {
        Exception exception = Assertions.assertThrows(EmptyNameException.class, () -> {
            ProductDto dto = new ProductDto("", "test product", new BigDecimal(100), 1L);
            Category category = new Category("Category");
            new Product(dto, category);
        });

        String expected = "Product name must not be blank";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must not create product with price equals 0")
    void mustNotCreateProductWithPriceEqualsZero() {
        Exception exception = Assertions.assertThrows(InvalidPriceException.class, () -> {
            ProductDto dto = new ProductDto("Product", "test product", BigDecimal.ZERO, 1L);
            Category category = new Category("Category");
            new Product(dto, category);
        });

        String expected = "The price must be greater than 0";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must not create product with negative price")
    void mustNotCreateProductWithPriceEqualsZeroNegativePrice() {
        Exception exception = Assertions.assertThrows(InvalidPriceException.class, () -> {
            ProductDto dto = new ProductDto("Product", "test product", new BigDecimal(-100), 1L);
            Category category = new Category("Category");
            new Product(dto, category);
        });

        String expected = "The price must be greater than 0";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }
}