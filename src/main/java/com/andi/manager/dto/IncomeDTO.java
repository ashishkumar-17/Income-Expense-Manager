package com.andi.manager.dto;

import com.andi.manager.model.Transaction;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncomeDTO {
    private Long id;

    @NotBlank(message = "Category is required")
    private String category;

    private List<Transaction> transactions;
}