package com.luizvictor.course.entities.orderItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luizvictor.course.entities.invoice.Invoice;
import com.luizvictor.course.entities.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_itens")
@NoArgsConstructor
@Getter
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Invoice invoice;

    public InvoiceItem(Integer quantity, Product product, Invoice invoice) {
        this.quantity = quantity;
        this.price = product.getPrice();
        this.product = product;
        this.invoice = invoice;
    }

    public BigDecimal getSubTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem invoiceItem = (InvoiceItem) o;
        return Objects.equals(id, invoiceItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
