package com.luizvictor.store.services;

import com.luizvictor.store.entities.Category;
import com.luizvictor.store.entities.product.Product;
import com.luizvictor.store.entities.product.dto.ProductDetailDto;
import com.luizvictor.store.entities.product.dto.ProductDto;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.repositories.CategoryRepository;
import com.luizvictor.store.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDetailDto> findAll() {
        return productRepository.findAll().stream().map(ProductDetailDto::new).toList();
    }

    public ProductDetailDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found!"));
        return new ProductDetailDto(product);
    }

    public ProductDetailDto save(ProductDto dto) {
        Category category = categoryRepository.getReferenceById(dto.category());
        Product product = new Product(dto, category);
        return new ProductDetailDto(productRepository.save(product));
    }
}
