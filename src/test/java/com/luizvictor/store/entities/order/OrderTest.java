package com.luizvictor.store.entities.order;

import com.luizvictor.store.entities.Category;
import com.luizvictor.store.entities.orderItem.OrderItem;
import com.luizvictor.store.entities.product.Product;
import com.luizvictor.store.entities.product.dto.ProductDto;
import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.entities.user.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


class OrderTest {
    @Test
    @DisplayName(value = "Must create order")
    void mustCreateOrder() {
        UserDto userDto = new UserDto("John Doe", "john@email.com", "1111", "123456");
        User user = new User(userDto);

        Order order = new Order(user);

        Assertions.assertEquals("John Doe", order.getUser().getName());
        Assertions.assertEquals("PAID", order.getOrderStatus().name());
    }

    @Test
    @DisplayName(value = "Must change order status")
    void mustChangeOrderStatus() {
        UserDto userDto = new UserDto("John Doe", "john@email.com", "1111", "123456");
        User user = new User(userDto);

        Order order = new Order(user);
        order.updateStatus("DELIVERED");

        Assertions.assertEquals("John Doe", order.getUser().getName());
        Assertions.assertEquals("DELIVERED", order.getOrderStatus().name());
    }

    @Test
    @DisplayName(value = "Must add itens to order")
    void mustAddOrderItemItensToOrder() {
        UserDto userDto = new UserDto("John Doe", "john@email.com", "1111", "123456");
        User user = new User(userDto);

        Order order = new Order(user);

        ProductDto dto = new ProductDto("Product", "test product", new BigDecimal(100), 1L);
        Category category = new Category("Category");
        Product product = new Product(dto, category);
        OrderItem orderItem = new OrderItem(2, product, order);

        order.addItem(orderItem);

        Assertions.assertEquals(1, order.getItens().size());
        Assertions.assertEquals(new BigDecimal(200), order.total());
    }
}