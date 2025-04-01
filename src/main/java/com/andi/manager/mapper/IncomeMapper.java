package com.andi.manager.mapper;

import com.andi.manager.dto.IncomeDTO;
import com.andi.manager.model.Income;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncomeMapper {

    // Entity to DTO
    public IncomeDTO toDTO(Income income){
        if (income == null) return null;

        return new IncomeDTO(income.getId(),
                income.getCategory(),
                income.getTransactions());
    }

    // DTO to Entity
    public Income toEntity(IncomeDTO incomeDTO){
        if (incomeDTO == null) return null;

        return new Income(incomeDTO.getId(),
                incomeDTO.getCategory(),
                incomeDTO.getTransactions());
    }

    // Entity list to DTO list
    public List<IncomeDTO> toDTOList(List<Income> incomeList){
        return incomeList.stream()
                .map(this::toDTO)
                .toList();
    }

    // DTO list to Entity list
    public List<Income> toEntityList(List<IncomeDTO> incomeDTOList){
        return incomeDTOList.stream()
                .map(this::toEntity)
                .toList();
    }
}