package com.luizvictor.store.repositories;

import com.luizvictor.store.entities.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
