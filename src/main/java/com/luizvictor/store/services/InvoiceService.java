package com.luizvictor.store.services;

import com.luizvictor.store.entities.invoice.Invoice;
import com.luizvictor.store.entities.invoice.dto.InvoiceDetailDto;
import com.luizvictor.store.entities.invoice.dto.InvoiceDto;
import com.luizvictor.store.entities.invoice.dto.InvoiceStatusDto;
import com.luizvictor.store.entities.orderItem.InvoiceItem;
import com.luizvictor.store.entities.orderItem.InvoiceItemDto;
import com.luizvictor.store.entities.product.Product;
import com.luizvictor.store.entities.user.User;
import com.luizvictor.store.exceptions.NotFoundException;
import com.luizvictor.store.repositories.InvoiceItemRepository;
import com.luizvictor.store.repositories.InvoiceRepository;
import com.luizvictor.store.repositories.ProductRepository;
import com.luizvictor.store.repositories.UserRepository;
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
    private final AuthenticationService authService;

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            InvoiceItemRepository orderItem, AuthenticationService authService) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.invoiceItemRepository = orderItem;
        this.authService = authService;
    }

    public List<InvoiceDetailDto> findAll() {
        return invoiceRepository.findAll().stream().map(InvoiceDetailDto::new).toList();
    }

    public InvoiceDetailDto findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found!"));
        authService.authUser(invoice.getUser().getId());
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
        } catch (EntityNotFoundException e) {
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
