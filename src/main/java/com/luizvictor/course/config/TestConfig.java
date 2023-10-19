package com.luizvictor.course.config;

import com.luizvictor.course.entities.*;
import com.luizvictor.course.repositories.CategoryRepository;
import com.luizvictor.course.repositories.OrderRepository;
import com.luizvictor.course.repositories.ProductRepository;
import com.luizvictor.course.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public TestConfig(
            UserRepository userRepository,
            OrderRepository orderRepository,
            CategoryRepository categoryRepository,
            ProductRepository productRepository
    ) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        User user1 = new User(null, "John Doe", "john@email.com", "1111-1111", "123");
        User user2 = new User(null, "Joanna Doe", "joanna@email.com", "1111-1111", "123");

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

        Order order1 = new Order(null, user1, OrderStatus.DELIVERED);
        Order order2 = new Order(null, user2, OrderStatus.PAID);
        Order order3 = new Order(null, user1, OrderStatus.CANCELED);


        userRepository.saveAll(Arrays.asList(user1, user2));
        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
        productRepository.saveAll(Arrays.asList(product1, product2, product3));
        orderRepository.saveAll(Arrays.asList(order1, order2, order3));
    }
}
