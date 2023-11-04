package com.luizvictor.store.repositories;

import com.luizvictor.store.entities.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
