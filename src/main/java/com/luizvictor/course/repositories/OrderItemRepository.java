package com.luizvictor.course.repositories;

import com.luizvictor.course.entities.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
