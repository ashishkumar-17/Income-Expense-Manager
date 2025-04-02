package com.manager.mapper;

import com.manager.dto.ExpenseDTO;
import com.manager.model.Expense;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseMapper {

    // Entity to DTO
    public ExpenseDTO toDTO(Expense expense){
        if (expense == null) return null;

        return new ExpenseDTO(expense.getId(),
                expense.getCategory(),
                expense.getTransactions());
    }

    // DTO to Entity
    public Expense toEntity(ExpenseDTO expenseDTO){
        if (expenseDTO == null) return null;

        return new Expense(expenseDTO.getId(),
                expenseDTO.getCategory(),
                expenseDTO.getTransactions());
    }


    // Entity list to DTO list
    public List<ExpenseDTO> toDTOList(List<Expense> expenseList){
        return expenseList.stream()
                .map(this::toDTO)
                .toList();
    }

    // DTO list to Entity list
    public List<Expense> toEntityList(List<ExpenseDTO> expenseDTOList){
        return expenseDTOList.stream()
                .map(this::toEntity)
                .toList();
    }
}