package com.luizvictor.store.entities.payment;

import com.luizvictor.store.entities.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private Transaction transaction;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Payment(String transaction, Order order) {
        this.transaction = Transaction.valueOf(transaction.toUpperCase());
        this.order = order;
    }
}
