package com.luizvictor.store.entities.product;

import com.luizvictor.store.entities.Category;
import com.luizvictor.store.entities.product.dto.ProductDto;
import com.luizvictor.store.exceptions.EmptyNameException;
import com.luizvictor.store.exceptions.InvalidPriceException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(ProductDto dto, Category category) {
        this.name = checkName(dto.name());
        this.description = dto.description();
        this.price = checkPrice(dto.price());
        this.category = category;
    }

    private String checkName(String name) {
        if (name.isEmpty()) {
            throw new EmptyNameException("Product name must not be blank");
        }

        return name;
    }

    private BigDecimal checkPrice(BigDecimal price) {
        int comparison = price.compareTo(BigDecimal.ZERO);
        if (comparison < 0 || comparison == 0) {
            throw new InvalidPriceException("The price must be greater than 0");
        }

        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
