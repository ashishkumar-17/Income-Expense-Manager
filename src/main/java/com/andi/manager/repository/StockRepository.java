package com.andi.manager.repository;

import com.andi.manager.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByItemNameAndVendorName(String itemName, String vendorName);
}