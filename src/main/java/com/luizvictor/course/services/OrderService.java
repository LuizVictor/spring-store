package com.luizvictor.course.services;

import com.luizvictor.course.entities.order.Order;
import com.luizvictor.course.entities.order.dto.OrderDetailDto;
import com.luizvictor.course.entities.order.dto.OrderDto;
import com.luizvictor.course.entities.orderItem.OrderItem;
import com.luizvictor.course.entities.orderItem.OrderItemDto;
import com.luizvictor.course.entities.product.Product;
import com.luizvictor.course.entities.user.User;
import com.luizvictor.course.exceptions.NotFoundException;
import com.luizvictor.course.repositories.OrderItemRepository;
import com.luizvictor.course.repositories.OrderRepository;
import com.luizvictor.course.repositories.ProductRepository;
import com.luizvictor.course.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderItemRepository orderItem
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItem;
    }

    public List<OrderDetailDto> findAll() {
        return orderRepository.findAll().stream().map(OrderDetailDto::new).toList();
    }

    public OrderDetailDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found!"));
        return new OrderDetailDto(order);
    }

    public OrderDetailDto save(OrderDto orderDto) {
        User user = userRepository.getReferenceById(orderDto.userId());
        Order order = orderRepository.save(new Order(user, orderDto.status()));
        List<OrderItem> items = saveItems(orderDto.items(), order);
        orderItemRepository.saveAll(items);

        return new OrderDetailDto(order);
    }

    private List<OrderItem> saveItems(List<OrderItemDto> itemDto, Order order) {
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDto item : itemDto) {
            Product product = productRepository.getReferenceById(item.productId());
            OrderItem orderItem = new OrderItem(item.quantity(), product, order);
            order.addItem(orderItem);
            items.add(orderItem);
        }
        return items;
    }
}
