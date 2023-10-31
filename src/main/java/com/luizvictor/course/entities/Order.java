package com.luizvictor.course.entities;

import com.luizvictor.course.entities.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
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
    private List<OrderItem> itens = new ArrayList<>();
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment = null;

    public Order(User user, OrderStatus orderStatus) {
        this.user = user;
        this.orderStatus = orderStatus;
    }

    public void addItem(OrderItem item) {
        item.setOrder(this);
        this.itens.add(item);
    }

    public BigDecimal getTotal() {
        return itens.stream().map(OrderItem::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
