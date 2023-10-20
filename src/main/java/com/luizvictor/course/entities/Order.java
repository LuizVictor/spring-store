package com.luizvictor.course.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "order")
    private final List<OrderItem> itens = new ArrayList<>();

    public void addItem(OrderItem item) {
        item.setOrder(this);
        this.itens.add(item);
    }
}
