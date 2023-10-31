package com.luizvictor.course.repositories;

import com.luizvictor.course.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
