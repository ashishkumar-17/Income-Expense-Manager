package com.manager.controller;

import com.manager.dto.ExpenseDTO;
import com.manager.dto.IncomeDTO;
import com.manager.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    private final FinanceService financeService;

    @Autowired
    public FinanceController(FinanceService financeService){
        this.financeService = financeService;
    }

    @PostMapping("/income")
    public ResponseEntity<IncomeDTO> addIncome(@RequestParam String category,
                                    @RequestParam double amount,
                                    @RequestParam String description){
        return ResponseEntity.ok(financeService.addIncome(category, amount, description));
    }

    @PostMapping("/expense")
    public ResponseEntity<ExpenseDTO> addExpense(@RequestParam String category,
                                 @RequestParam double amount,
                                 @RequestParam String description){
        return ResponseEntity.ok(financeService.addExpense(category, amount, description));
    }

    @GetMapping("/net")
    public ResponseEntity<String> calculateNet(){
        return ResponseEntity.ok(financeService.calculateNet());
    }
}