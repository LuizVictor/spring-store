package com.luizvictor.course.resources;

import com.luizvictor.course.entities.invoice.dto.InvoiceDetailDto;
import com.luizvictor.course.entities.invoice.dto.InvoiceDto;
import com.luizvictor.course.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/invoices")
public class InvoiceResource {
    private final InvoiceService invoiceService;

    public InvoiceResource(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDetailDto>> findAll() {
        List<InvoiceDetailDto> orders = invoiceService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InvoiceDetailDto> findById(@PathVariable Long id) {
        InvoiceDetailDto order = invoiceService.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<InvoiceDetailDto> save(@RequestBody InvoiceDto invoiceDto, UriComponentsBuilder uriBuilder) {
        InvoiceDetailDto order = invoiceService.save(invoiceDto);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(order.id()).toUri();
        return ResponseEntity.created(uri).body(order);
    }
}
