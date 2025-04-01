package com.andi.manager.dto;

import com.andi.manager.model.Transaction;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;

    @NotBlank(message = "Category is required")
    private String category;

    private List<Transaction> transactions;
}