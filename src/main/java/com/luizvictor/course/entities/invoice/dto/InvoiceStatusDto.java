package com.luizvictor.course.entities.invoice.dto;


import jakarta.validation.constraints.NotBlank;

public record InvoiceStatusDto(@NotBlank String status) {
}
