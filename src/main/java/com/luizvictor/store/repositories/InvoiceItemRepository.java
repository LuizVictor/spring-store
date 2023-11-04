package com.luizvictor.store.repositories;

import com.luizvictor.store.entities.orderItem.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}
