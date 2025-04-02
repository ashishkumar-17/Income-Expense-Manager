package com.manager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {
    private Long id;
    private double amount;
    private LocalDate date;
    private String description;
}