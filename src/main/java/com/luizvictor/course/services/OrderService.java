package com.luizvictor.course.services;

import com.luizvictor.course.entities.order.Order;
import com.luizvictor.course.entities.order.OrderDetailDto;
import com.luizvictor.course.exceptions.NotFoundException;
import com.luizvictor.course.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDetailDto> findAll() {
        return orderRepository.findAll().stream().map(OrderDetailDto::new).toList();
    }

    public OrderDetailDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found!"));
        return new OrderDetailDto(order);
    }
}
