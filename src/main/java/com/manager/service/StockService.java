package com.manager.service;

import com.manager.dto.StockDTO;
import com.manager.mapper.StockMapper;
import com.manager.model.Stock;
import com.manager.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Autowired
    public StockService(StockRepository stockRepository,
                        StockMapper stockMapper){
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    // Add new purchase
    public StockDTO purchase(String itemName, String vendorName, int quantity, double costPrice){
        Stock stock = new Stock();
        stock.setItemName(itemName);
        stock.setVendorName(vendorName);
        stock.setQuantity(quantity);
        stock.setCostPrice(costPrice);
        stock.setPurchase(true);
        stock.setDate(LocalDate.now());
        stock.setCurrentStock(calculateCurrentStock(itemName, vendorName) + quantity);

        return stockMapper.toDTO(stockRepository.save(stock));
    }

    // Add new sale
    public StockDTO sale(String itemName, String vendorName, int quantity, double sellPrice){
        int currentStock = calculateCurrentStock(itemName, vendorName);

        if (currentStock >= quantity){
            Stock stock = new Stock();
            stock.setItemName(itemName);
            stock.setVendorName(vendorName);
            stock.setQuantity(quantity);
            stock.setPurchase(false);
            stock.setSalePrice(sellPrice);
            stock.setDate(LocalDate.now());
            stock.setCurrentStock(currentStock - quantity);
            return stockMapper.toDTO(stockRepository.save(stock));
        }
        throw new RuntimeException("Insufficient stock");
    }

    // calculate current stock holding
    private int calculateCurrentStock(String itemName, String vendorName) {
        List<Stock> stocks = stockRepository.findByItemNameAndVendorName(itemName, vendorName);

        int totalPurchased = stocks.stream()
                .filter(Stock::isPurchase)
                .mapToInt(Stock::getQuantity).sum();

        int totalSold = stocks.stream()
                .filter(Stock -> !Stock.isPurchase())
                .mapToInt(Stock::getQuantity).sum();

        return totalPurchased - totalSold;
    }
}