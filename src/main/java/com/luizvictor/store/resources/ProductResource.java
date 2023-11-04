package com.luizvictor.store.resources;

import com.luizvictor.store.entities.product.dto.ProductDetailDto;
import com.luizvictor.store.entities.product.dto.ProductDto;
import com.luizvictor.store.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDetailDto>> findAll() {
        List<ProductDetailDto> products = productService.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDetailDto> findById(@PathVariable Long id) {
        ProductDetailDto product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<ProductDetailDto> save(@RequestBody @Valid ProductDto dto, UriComponentsBuilder uriBuilder) {
        ProductDetailDto product = productService.save(dto);
        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.id()).toUri();
        return ResponseEntity.created(uri).body(product);
    }
}