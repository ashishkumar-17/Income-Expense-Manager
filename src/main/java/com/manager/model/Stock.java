package com.manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String vendorName;
    private int quantity;
    private double costPrice; // for purchase
    private double salePrice; // for sales
    private boolean isPurchase; // true for purchase, false for sale
    private LocalDate date;
    private int currentStock; // updated per item and vendor

}
