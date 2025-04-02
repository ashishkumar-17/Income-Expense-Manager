package com.manager.dto;

import com.manager.model.Invoice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class InvoiceDTO {
    private Long id;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotEmpty(message = "Items list cannot be empty")
    private List<Invoice.Item> items;

    private String logoPath; // Optional: If not provided, use default company logo

    @Data
    public static class ItemDTO{
        @NotBlank(message = "Item name is required")
        private String name;

        @Positive(message = "Quantity must be positive")
        private int quantity;

        @Positive(message = "Price must be positive")
        private double price;
    }
}