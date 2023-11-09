package com.luizvictor.store.entities.orderItem;

import com.luizvictor.store.entities.order.Order;
import com.luizvictor.store.entities.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemTest {
    @Test
    @DisplayName(value = "Must create Order item")
    void mustCreateOrderItem(){
        Product product = mock(Product.class);
        Order order = mock(Order.class);
        OrderItem orderItem = new OrderItem(2, product, order);

        assertEquals(2, orderItem.getQuantity());
    }

    @Test
    @DisplayName(value = "Subtotal must return 30")
    void subTotalMust30() {
        Product product = mock(Product.class);
        Order order = mock(Order.class);

        when(product.getPrice()).thenReturn(new BigDecimal(15));

        OrderItem orderItem = new OrderItem(2, product, order);

        assertEquals(new BigDecimal(30), orderItem.subTotal());
    }
}