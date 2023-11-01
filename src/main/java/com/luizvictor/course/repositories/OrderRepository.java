package com.luizvictor.course.repositories;

import com.luizvictor.course.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
