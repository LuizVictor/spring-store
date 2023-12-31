package com.luizvictor.store.config;

import com.luizvictor.store.entities.Category;
import com.luizvictor.store.entities.order.Order;
import com.luizvictor.store.entities.orderItem.OrderItem;
import com.luizvictor.store.entities.product.Product;
import com.luizvictor.store.entities.product.dto.ProductDto;
import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.entities.user.dto.UserDto;
import com.luizvictor.store.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
@Profile("dev")
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

    private User createUser(String name, String email, String phone, String password) {
        UserDto userDto = new UserDto(name, email, phone, password);
        return new User(userDto);
    }

    private Product createProduct(String name, String description, String price, Category category) {
        ProductDto productDto = new ProductDto(name, description, new BigDecimal(price), category.getId());
        return new Product(productDto, category);
    }

    //    @Override
    public void run(String... args) {
        User user1 = createUser("John Doe", "john@email.com", "111-111", "123456");
        User user2 = createUser("Joanna Doe", "joanna@email.com", "111-222", "654321");
        user1.changeRole("ADMIN");

        Category category1 = new Category("Computers");
        Category category2 = new Category("Books");
        Category category3 = new Category("Electronics");

        Product product1 = createProduct("Macbook", "apple notebook", "1000", category1);
        Product product2 = createProduct("Iphone", "apple smartphone", "800", category3);
        Product product3 = createProduct("The man in the high castle", "dystopic book", "30", category2);

        Order order1 = new Order(user1);
        Order order2 = new Order(user2);
        Order order3 = new Order(user1);

        order3.updateStatus("canceled");

        OrderItem orderItem1 = new OrderItem(2, product3, order1);
        OrderItem orderItem2 = new OrderItem(1, product2, order1);

        order1.addItem(orderItem1);
        order2.addItem(orderItem2);

        userRepository.saveAll(Arrays.asList(user1, user2));
        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
        productRepository.saveAll(Arrays.asList(product1, product2, product3));
        orderRepository.saveAll(Arrays.asList(order1, order2, order3));
        orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2));
    }
}
