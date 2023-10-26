package com.luizvictor.course.config;

import com.luizvictor.course.entities.*;
import com.luizvictor.course.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) {
        User user1 = new User(
                null,
                "John Doe",
                "john@email.com",
                "1111-1111",
                "123"
        );

        User user2 = new User(
                null,
                "Joanna Doe",
                "joanna@email.com",
                "1111-1111",
                "123"
        );

        Category category1 = new Category(null, "Computers");
        Category category2 = new Category(null, "Books");
        Category category3 = new Category(null, "Electronics");

        Product product1 = new Product(
                null,
                "Macbook",
                "apple notebook",
                new BigDecimal("1000"),
                "url_image",
                category1
        );

        Product product2 = new Product(
                null,
                "Iphone",
                "apple smartphone",
                new BigDecimal("999"),
                "url_image",
                category3
        );

        Product product3 = new Product(
                null,
                "The man in the high castle",
                "dystopic book",
                new BigDecimal("30"),
                "url_image",
                category2
        );

        Order order1 = new Order(user1, OrderStatus.DELIVERED);
        Order order2 = new Order(user2, OrderStatus.PAID);
        Order order3 = new Order(user1, OrderStatus.CANCELED);

        OrderItem orderItem1 = new OrderItem(1, product3, order1);
        OrderItem orderItem2 = new OrderItem(2, product2, order1);

        order1.addItem(orderItem1);
        order2.addItem(orderItem2);

        userRepository.saveAll(Arrays.asList(user1, user2));
        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
        productRepository.saveAll(Arrays.asList(product1, product2, product3));
        orderRepository.saveAll(Arrays.asList(order1, order2, order3));
        orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2));

        Payment payment = new Payment(null, order2);
        order2.setPayment(payment);
        orderRepository.save(order2);
    }
}
