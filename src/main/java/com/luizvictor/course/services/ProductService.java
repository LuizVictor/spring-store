package com.luizvictor.course.services;

import com.luizvictor.course.entities.Category;
import com.luizvictor.course.entities.product.Product;
import com.luizvictor.course.entities.product.dto.ProductDetailDto;
import com.luizvictor.course.entities.product.dto.ProductDto;
import com.luizvictor.course.exceptions.NotFountException;
import com.luizvictor.course.repositories.CategoryRepository;
import com.luizvictor.course.repositories.ProductRepository;
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
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFountException("Product not found!"));
        return new ProductDetailDto(product);
    }

    public ProductDetailDto save(ProductDto productDto) {
        Category category = categoryRepository.getReferenceById(productDto.category());
        Product product = new Product(productDto, category);
        return new ProductDetailDto(productRepository.save(product));
    }
}
