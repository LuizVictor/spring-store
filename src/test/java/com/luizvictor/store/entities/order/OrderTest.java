package com.luizvictor.store.entities.order;

import com.luizvictor.store.entities.orderItem.OrderItem;
import com.luizvictor.store.entities.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;


class OrderTest {
    @Test
    @DisplayName(value = "Must create order")
    void mustCreateOrder() {
        User user = mock(User.class);
        Order order = new Order(user);

        assertSame(user, order.getUser());
        assertEquals("PAID", order.getOrderStatus().name());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    @DisplayName(value = "Must change order status")
    void mustChangeOrderStatus() {
        User user = mock(User.class);

        Order order = new Order(user);
        order.updateStatus("DELIVERED");

        assertEquals("DELIVERED", order.getOrderStatus().name());
    }

    @Test
    @DisplayName(value = "Must add itens to order")
    void mustAddOrderItemItensToOrder() {
        User user = mock(User.class);

        Order order = new Order(user);
        OrderItem orderItem1 = mock(OrderItem.class);
        OrderItem orderItem2 = mock(OrderItem.class);

        order.addItem(orderItem1);
        order.addItem(orderItem2);
        when(orderItem1.subTotal()).thenReturn(new BigDecimal(100));
        when(orderItem2.subTotal()).thenReturn(new BigDecimal(200));

        assertEquals(2, order.getItens().size());
        assertEquals(new BigDecimal(300), order.total());
    }
}