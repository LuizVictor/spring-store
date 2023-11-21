package com.luizvictor.store.entities.order;

import com.luizvictor.store.entities.orderItem.OrderItem;
import com.luizvictor.store.entities.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> itens = new ArrayList<>();
    private final LocalDateTime createdAt = LocalDateTime.now();

    public Order(User user) {
        this.user = user;
        this.orderStatus = OrderStatus.WAITING_PAYMENT;
    }

    public void updateStatus(String status) {
        this.orderStatus = OrderStatus.valueOf(status.toUpperCase());
    }

    public void addItem(OrderItem item) {
        item.includeOrder(this);
        this.itens.add(item);
    }

    public BigDecimal total() {
        return itens.stream().map(OrderItem::subTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
