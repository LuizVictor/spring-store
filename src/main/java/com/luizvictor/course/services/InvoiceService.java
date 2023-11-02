package com.luizvictor.course.services;

import com.luizvictor.course.entities.invoice.Invoice;
import com.luizvictor.course.entities.invoice.dto.InvoiceDetailDto;
import com.luizvictor.course.entities.invoice.dto.InvoiceDto;
import com.luizvictor.course.entities.invoice.dto.InvoiceStatusDto;
import com.luizvictor.course.entities.orderItem.InvoiceItem;
import com.luizvictor.course.entities.orderItem.InvoiceItemDto;
import com.luizvictor.course.entities.product.Product;
import com.luizvictor.course.entities.user.User;
import com.luizvictor.course.exceptions.NotFoundException;
import com.luizvictor.course.repositories.InvoiceItemRepository;
import com.luizvictor.course.repositories.InvoiceRepository;
import com.luizvictor.course.repositories.ProductRepository;
import com.luizvictor.course.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            InvoiceItemRepository orderItem
    ) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.invoiceItemRepository = orderItem;
    }

    public List<InvoiceDetailDto> findAll() {
        return invoiceRepository.findAll().stream().map(InvoiceDetailDto::new).toList();
    }

    public InvoiceDetailDto findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found!"));
        return new InvoiceDetailDto(invoice);
    }

    public InvoiceDetailDto save(InvoiceDto dto) {
        User user = userRepository.getReferenceById(dto.userId());
        Invoice invoice = invoiceRepository.save(new Invoice(user));
        List<InvoiceItem> items = saveItems(dto.items(), invoice);
        invoiceItemRepository.saveAll(items);

        return new InvoiceDetailDto(invoice);
    }

    public InvoiceDetailDto updateStatus(Long id, InvoiceStatusDto dto) {
        try {
            Invoice invoice = invoiceRepository.getReferenceById(id);
            invoice.updateStatus(dto.status());
            return new InvoiceDetailDto(invoiceRepository.save(invoice));
        }  catch (EntityNotFoundException e) {
            throw new NotFoundException("User not found");
        }
    }

    private List<InvoiceItem> saveItems(List<InvoiceItemDto> dto, Invoice invoice) {
        List<InvoiceItem> items = new ArrayList<>();
        for (InvoiceItemDto item : dto) {
            Product product = productRepository.getReferenceById(item.productId());
            InvoiceItem invoiceItem = new InvoiceItem(item.quantity(), product, invoice);
            invoice.addItem(invoiceItem);
            items.add(invoiceItem);
        }
        return items;
    }
}
