package com.luizvictor.store.services;

import com.luizvictor.store.entities.order.Order;
import com.luizvictor.store.entities.order.OrderStatus;
import com.luizvictor.store.entities.order.dto.OrderDetailsDto;
import com.luizvictor.store.entities.payment.Payment;
import com.luizvictor.store.entities.payment.PaymentDetailsDto;
import com.luizvictor.store.entities.payment.PaymentDto;
import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.repositories.OrderItemRepository;
import com.luizvictor.store.repositories.OrderRepository;
import com.luizvictor.store.repositories.PaymentRepository;
import com.luizvictor.store.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.luizvictor.store.common.OrderConstants.EMPTY_ORDER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    @Test
    @DisplayName(value = "Must save order")
    void save_withValidOrder_mustReturnOrderDetailsDto() {
        User user = mock(User.class);
        Order order = new Order(user);

        when(userRepository.getReferenceById(any())).thenReturn(user);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderItemRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        OrderDetailsDto details = orderService.save(EMPTY_ORDER);

        assertNotNull(details);
        assertEquals(0, details.items().size());
        assertEquals(BigDecimal.ZERO, details.total());
        assertEquals("WAITING_PAYMENT", details.status());
    }

    @Test
    @DisplayName(value = "Must return list of orders saved")
    void findAll_withDataSaved_mustReturnListOfOrderDetailsDto() {
        User user = mock(User.class);
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        when(order1.getUser()).thenReturn(user);
        when(order2.getUser()).thenReturn(user);
        when(order1.getOrderStatus()).thenReturn(OrderStatus.PAID);
        when(order2.getOrderStatus()).thenReturn(OrderStatus.PAID);

        List<OrderDetailsDto> details = orderService.findAll();

        assertNotNull(details);
        assertEquals(2, details.size());
    }

    @Test
    @DisplayName(value = "Must throw NotFoundException if repository is empty")
    void findAll_withEmptyData_mustThrowNotFoundException() {
        when(orderRepository.findAll()).thenReturn(null);

        assertThrows(NotFoundException.class, () -> orderService.findAll());
    }

    @Test
    @DisplayName(value = "Must find order by id")
    void findById_withExistingId_mustReturnOrderDetailsDto() {
        User user = mock(User.class);
        Order order = new Order(user);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        OrderDetailsDto details = orderService.findById(1L);

        assertNotNull(details);
        assertEquals(0, details.items().size());
        assertEquals(BigDecimal.ZERO, details.total());
        assertEquals("WAITING_PAYMENT", details.status());
    }

    @Test
    @DisplayName(value = "Must throw NotFoundException if order id not exist")
    void findById_noExistingId_mustThrowNotFoundException() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.findById(1L));
    }

    @Test
    @DisplayName(value = "Must update order status")
    void updateStatus_withExistingOrder_mustReturnOrderDetailsDtoWithStatusDelivered() {
        User user = mock(User.class);
        Order order = new Order(user);

        when(orderRepository.getReferenceById(anyLong())).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDetailsDto details = orderService.updateStatus(1L, "delivered");

        assertNotNull(details);
        assertEquals(0, details.items().size());
        assertEquals(BigDecimal.ZERO, details.total());
        assertEquals("DELIVERED", details.status());
    }

    @Test
    @DisplayName(value = "Must pay order")
    void pay_mustReturnPaymentDetailsDto() {
        Order order = mock(Order.class);
        Payment payment = new Payment("credit_card", order);

        when(orderRepository.getReferenceById(anyLong())).thenReturn(order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(order.getOrderStatus()).thenReturn(OrderStatus.PAID);

        PaymentDto paymentDto = new PaymentDto("credit_card", 1L);
        PaymentDetailsDto details = orderService.payOrder(paymentDto);

        assertNotNull(details);
        assertEquals(details.transaction(), "CREDIT_CARD");
        assertEquals(details.orderStatus(), "PAID");
    }

    @Test
    @DisplayName(value = "Must throw NotFoundException when trying to update status with no existing ID")
    void updateStatus_withNonExistingId_mustThrowNotFoundException() {
        when(orderRepository.getReferenceById(anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> orderService.updateStatus(1L, "delivered"));
    }
}