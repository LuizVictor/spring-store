package com.luizvictor.store.entities.product.dto;

import com.luizvictor.store.entities.product.Product;

import java.math.BigDecimal;

public record ProductDetailDto(Long id, String name, String description, BigDecimal price, String category) {
    public ProductDetailDto(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getName()
        );
    }
}