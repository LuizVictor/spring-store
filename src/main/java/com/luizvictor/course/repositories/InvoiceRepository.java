package com.luizvictor.course.repositories;

import com.luizvictor.course.entities.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
