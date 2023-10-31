package com.luizvictor.course.entities.product.dto;

import java.math.BigDecimal;

public record ProductDto(String name, String description, BigDecimal price, Long category) {
}
