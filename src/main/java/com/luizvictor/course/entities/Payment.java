package com.luizvictor.course.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final LocalDateTime createdAt = LocalDateTime.now();
    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;
}
