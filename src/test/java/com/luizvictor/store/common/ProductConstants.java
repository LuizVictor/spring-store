package com.luizvictor.store.common;

import com.luizvictor.store.entities.product.dto.ProductDto;

import java.math.BigDecimal;

public class ProductConstants {
    public static ProductDto PRODUCT = new ProductDto("Product", "test product", new BigDecimal(100), 1L);
    public static ProductDto INVALID_NAME_PRODUCT = new ProductDto("", "test product", new BigDecimal(100), 1L);
    public static ProductDto INVALID_PRICE_ZERO_PRODUCT = new ProductDto("Product", "test product", BigDecimal.ZERO, 1L);
    public static ProductDto INVALID_PRICE_NEGATIVE_PRODUCT = new ProductDto("Product", "test product", new BigDecimal(-100), 1L);
}
