package com.luizvictor.course.entities.product.dto;

import com.luizvictor.course.entities.product.Product;

import java.math.BigDecimal;

public record ProductDetailDto(Long id, String name, String description, BigDecimal price, Long category) {
    public ProductDetailDto(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getId()
        );
    }
}