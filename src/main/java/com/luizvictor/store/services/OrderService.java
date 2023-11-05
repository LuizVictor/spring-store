package com.luizvictor.store.services;

import com.luizvictor.store.entities.order.Order;
import com.luizvictor.store.entities.order.dto.OrderDetailDto;
import com.luizvictor.store.entities.order.dto.OrderDto;
import com.luizvictor.store.entities.order.dto.OrderStatusDto;
import com.luizvictor.store.entities.orderItem.OrderItem;
import com.luizvictor.store.entities.orderItem.OrderItemDto;
import com.luizvictor.store.entities.product.Product;
import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.repositories.OrderItemRepository;
import com.luizvictor.store.repositories.OrderRepository;
import com.luizvictor.store.repositories.ProductRepository;
import com.luizvictor.store.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final AuthenticationService authService;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderItemRepository orderItem, AuthenticationService authService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItem;
        this.authService = authService;
    }

    public List<OrderDetailDto> findAll() {
        return orderRepository.findAll().stream().map(OrderDetailDto::new).toList();
    }

    public OrderDetailDto findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found!"));
        authService.authUser(order.getUser().getId());
        return new OrderDetailDto(order);
    }

    public OrderDetailDto save(OrderDto dto) {
        User user = userRepository.getReferenceById(dto.userId());
        Order order = orderRepository.save(new Order(user));
        List<OrderItem> items = saveItems(dto.items(), order);
        orderItemRepository.saveAll(items);

        return new OrderDetailDto(order);
    }

    public OrderDetailDto updateStatus(Long id, OrderStatusDto dto) {
        try {
            Order order = orderRepository.getReferenceById(id);
            order.updateStatus(dto.status());
            return new OrderDetailDto(orderRepository.save(order));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("User not found");
        }
    }

    private List<OrderItem> saveItems(List<OrderItemDto> dto, Order order) {
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDto item : dto) {
            Product product = productRepository.getReferenceById(item.productId());
            OrderItem orderItem = new OrderItem(item.quantity(), product, order);
            order.addItem(orderItem);
            items.add(orderItem);
        }
        return items;
    }
}
