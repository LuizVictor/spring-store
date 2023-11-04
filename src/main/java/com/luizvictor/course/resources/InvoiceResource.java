package com.luizvictor.course.resources;

import com.luizvictor.course.entities.invoice.dto.InvoiceDetailDto;
import com.luizvictor.course.entities.invoice.dto.InvoiceDto;
import com.luizvictor.course.entities.invoice.dto.InvoiceStatusDto;
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
        List<InvoiceDetailDto> invoices = invoiceService.findAll();
        return ResponseEntity.ok().body(invoices);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InvoiceDetailDto> findById(@PathVariable Long id) {
        InvoiceDetailDto invoice = invoiceService.findById(id);
        return ResponseEntity.ok().body(invoice);
    }

    @PostMapping
    public ResponseEntity<InvoiceDetailDto> save(@RequestBody InvoiceDto dto, UriComponentsBuilder uriBuilder) {
        InvoiceDetailDto invoice = invoiceService.save(dto);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(invoice.id()).toUri();
        return ResponseEntity.created(uri).body(invoice);
    }

    @PatchMapping("{id}")
    public ResponseEntity<InvoiceDetailDto> updateStatus(@PathVariable Long id, @RequestBody InvoiceStatusDto dto) {
        InvoiceDetailDto invoice = invoiceService.updateStatus(id, dto);
        return ResponseEntity.ok().body(invoice);
    }
}
