package com.luizvictor.store.entities.payment;

import com.luizvictor.store.entities.order.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    @DisplayName(value = "Create payment")
    void mustCreatePayment() {
        Order order = mock(Order.class);
        Payment payment = new Payment("credit_card", order);

        assertEquals(order, payment.getOrder());
        assertEquals("CREDIT_CARD", payment.getTransaction().name());
        assertNotNull(payment.getCreatedAt());
    }
}