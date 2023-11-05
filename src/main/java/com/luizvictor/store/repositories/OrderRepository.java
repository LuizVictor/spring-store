package com.luizvictor.store.repositories;

import com.luizvictor.store.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
