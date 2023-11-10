package com.luizvictor.store.entities.product;

import com.luizvictor.store.entities.Category;
import com.luizvictor.store.exceptions.EmptyNameException;
import com.luizvictor.store.exceptions.InvalidPriceException;

import static com.luizvictor.store.common.ProductConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductTest {
    @Test
    @DisplayName(value = "Must create product")
    void mustCreateProduct() {
        Category category = new Category("Category");
        Product product = new Product(PRODUCT, category);

        assertEquals("Product", product.getName());
        assertEquals("test product", product.getDescription());
        assertEquals(BigDecimal.valueOf(100), product.getPrice());
    }

    @Test
    @DisplayName(value = "Must not create product with empty name")
    void mustNotCreateProductWithEmptyName() {
        Exception exception = assertThrows(EmptyNameException.class, () -> {
            Category category = new Category("Category");
            new Product(INVALID_NAME_PRODUCT, category);
        });

        String expected = "Product name must not be blank";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must not create product with price equals 0")
    void mustNotCreateProductWithPriceEqualsZero() {
        Exception exception = assertThrows(InvalidPriceException.class, () -> {
            Category category = new Category("Category");
            new Product(INVALID_PRICE_ZERO_PRODUCT, category);
        });

        String expected = "The price must be greater than 0";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Must not create product with negative price")
    void mustNotCreateProductWithPriceEqualsZeroNegativePrice() {
        Exception exception = assertThrows(InvalidPriceException.class, () -> {
            Category category = new Category("Category");
            new Product(INVALID_PRICE_NEGATIVE_PRODUCT, category);
        });

        String expected = "The price must be greater than 0";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }
}