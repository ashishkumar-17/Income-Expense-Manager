package com.andi.manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StockDTO {
    private Long id;

    @NotBlank(message = "Item name is required")
    private String itemName;

    @NotBlank(message = "Vendor name is required")
    private String vendorName;

    @Positive(message = "Quantity must be positive")
    private int quantity;

    @PositiveOrZero(message = "Cost price must be non-negative")
    private double costPrice;

    @PositiveOrZero(message = "Selling price must be non-negative")
    private double salePrice;

    private boolean isPurchase;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private int currentStock;

    public StockDTO() {

    }
}