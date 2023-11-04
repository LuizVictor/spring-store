package com.luizvictor.store.repositories;

import com.luizvictor.store.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
