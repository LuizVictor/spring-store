package com.luizvictor.store.entities.product.dto;

import com.luizvictor.store.entities.product.Product;

import java.math.BigDecimal;

public record ProductDetailsDto(Long id, String name, String description, BigDecimal price, String category) {
    public ProductDetailsDto(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getName()
        );
    }
}
