package com.luizvictor.store.entities.payment;

import java.time.LocalDateTime;

public record PaymentDetailsDto(Long id, String transaction, Long orderId, String orderStatus, LocalDateTime createdAt) {
    public PaymentDetailsDto(Payment payment) {
       this(
               payment.getId(),
               payment.getTransaction().name(),
               payment.getOrder().getId(),
               payment.getOrder().getOrderStatus().name(),
               payment.getCreatedAt()
       );
    }
}
