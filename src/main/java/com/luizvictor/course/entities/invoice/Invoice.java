package com.luizvictor.course.entities.invoice;

import com.luizvictor.course.entities.orderItem.InvoiceItem;
import com.luizvictor.course.entities.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(value = EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<InvoiceItem> itens = new ArrayList<>();
    private final LocalDateTime createdAt = LocalDateTime.now();

    public Invoice(User user) {
        this.user = user;
        this.invoiceStatus = InvoiceStatus.PAID;
    }

    public void updateStatus(InvoiceStatus status) {
        this.invoiceStatus = status;
    }

    public void addItem(InvoiceItem item) {
        item.setInvoice(this);
        this.itens.add(item);
    }

    public BigDecimal getTotal() {
        return itens.stream().map(InvoiceItem::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
