package com.andi.manager.controller;

import com.andi.manager.dto.StockDTO;
import com.andi.manager.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<StockDTO> purchase(@RequestParam String itemName,
                                   @RequestParam String vendorName,
                                   @RequestParam int quantity,
                                   @RequestParam double costPrice){
        return ResponseEntity.ok(stockService.purchase(itemName, vendorName, quantity, costPrice));
    }

    @PostMapping("/sale")
    public ResponseEntity<StockDTO> sale(@RequestParam String itemName,
                         @RequestParam String vendorName,
                         @RequestParam int quantity,
                         @RequestParam double sellPrice){
        return ResponseEntity.ok(stockService.sale(itemName, vendorName, quantity, sellPrice));
    }
}