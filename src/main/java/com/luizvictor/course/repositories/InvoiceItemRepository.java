package com.luizvictor.course.repositories;

import com.luizvictor.course.entities.orderItem.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}
