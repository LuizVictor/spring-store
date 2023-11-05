package com.luizvictor.store.repositories;

import com.luizvictor.store.entities.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
