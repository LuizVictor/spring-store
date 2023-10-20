package com.luizvictor.course.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_itens")
@NoArgsConstructor
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Integer quantity, Product product, Order order) {
        this.quantity = quantity;
        this.price = product.getPrice();
        this.product = product;
        this.order = order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
