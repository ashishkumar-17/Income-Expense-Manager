package com.manager.mapper;

import com.manager.dto.StockDTO;
import com.manager.model.Stock;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockMapper {

    // Entity to DTO
    public StockDTO toDTO(Stock stock){
        if (stock == null) return null;

        return new StockDTO(stock.getId(),
                stock.getItemName(),
                stock.getVendorName(),
                stock.getQuantity(),
                stock.getCostPrice(),
                stock.getSalePrice(),
                stock.isPurchase(),
                stock.getDate(),
                stock.getCurrentStock());
    }

    // DTO to Entity
    public Stock toEntity(StockDTO stockDTO){
        if (stockDTO == null) return null;

        return new Stock(stockDTO.getId(),
                stockDTO.getItemName(),
                stockDTO.getVendorName(),
                stockDTO.getQuantity(),
                stockDTO.getCostPrice(),
                stockDTO.getSalePrice(),
                stockDTO.isPurchase(),
                stockDTO.getDate(),
                stockDTO.getCurrentStock());
    }

    // Entity list to DTO list
    public List<StockDTO> toDTOList(List<Stock> stockList){
        return stockList.stream()
                .map(this::toDTO)
                .toList();
    }

    // DTO list to Entity list
    public List<Stock> toEntityList(List<StockDTO> stockDTOList){
        return stockDTOList.stream()
                .map(this::toEntity)
                .toList();
    }
}