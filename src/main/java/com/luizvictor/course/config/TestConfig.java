package com.luizvictor.course.config;

import com.luizvictor.course.entities.Category;
import com.luizvictor.course.entities.Order;
import com.luizvictor.course.entities.OrderStatus;
import com.luizvictor.course.entities.User;
import com.luizvictor.course.repositories.CategoryRepository;
import com.luizvictor.course.repositories.OrderRepository;
import com.luizvictor.course.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;

    public TestConfig(
            UserRepository userRepository,
            OrderRepository orderRepository,
            CategoryRepository categoryRepository
    ) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        User user1 = new User(null, "John Doe", "john@email.com", "1111-1111", "123");
        User user2 = new User(null, "Joanna Doe", "joanna@email.com", "1111-1111", "123");

        Order order1 = new Order(null, user1, OrderStatus.DELIVERED);
        Order order2 = new Order(null, user2, OrderStatus.PAID);
        Order order3 = new Order(null, user1, OrderStatus.CANCELED);

        Category category1 = new Category(null, "Computers");
        Category category2 = new Category(null, "Books");
        Category category3 = new Category(null, "Electronics");

        userRepository.saveAll(Arrays.asList(user1, user2));
        orderRepository.saveAll(Arrays.asList(order1, order2, order3));
        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
    }
}
