package com.luizvictor.store.services;

import com.luizvictor.store.entities.Category;
import com.luizvictor.store.entities.product.Product;
import com.luizvictor.store.entities.product.dto.ProductDetailsDto;
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

    public List<ProductDetailsDto> findAll() {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new NotFoundException("Products not found!");
            }
            return products.stream().map(ProductDetailsDto::new).toList();
        } catch (NullPointerException e) {
            throw new NotFoundException("Products not found!");
        }
    }

    public ProductDetailsDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found!"));
        return new ProductDetailsDto(product);
    }

    public ProductDetailsDto save(ProductDto dto) {
        Category category = categoryRepository.findById(dto.category()).orElseThrow(() -> new NotFoundException("Category not found"));
        Product product = new Product(dto, category);
        return new ProductDetailsDto(productRepository.save(product));
    }
}
