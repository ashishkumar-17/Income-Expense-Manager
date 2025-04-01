package com.andi.manager.service;

import com.andi.manager.dto.ExpenseDTO;
import com.andi.manager.dto.IncomeDTO;
import com.andi.manager.mapper.ExpenseMapper;
import com.andi.manager.mapper.IncomeMapper;
import com.andi.manager.model.Expense;
import com.andi.manager.model.Income;
import com.andi.manager.model.Transaction;
import com.andi.manager.repository.ExpenseRepository;
import com.andi.manager.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class FinanceService {
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final IncomeMapper incomeMapper;
    private final ExpenseMapper expenseMapper;

    @Autowired
    public FinanceService(IncomeRepository incomeRepository,
                          ExpenseRepository expenseRepository,
                          IncomeMapper incomeMapper,
                          ExpenseMapper expenseMapper){
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.incomeMapper = incomeMapper;
        this.expenseMapper = expenseMapper;
    }


    // Add new Income
    public IncomeDTO addIncome(String category, double amount, String description){
        Income income = incomeRepository.findByCategory(category).orElse(new Income());

        income.setCategory(category);
        if (income.getTransactions() == null) income.setTransactions(new ArrayList<>());

        income.getTransactions().add(new Transaction(amount, LocalDate.now(), description));

        return incomeMapper.toDTO(incomeRepository.save(income));
    }

    // Add new Expense
    public ExpenseDTO addExpense(String category, double amount, String description){
        Expense expense = expenseRepository.findByCategory(category).orElse(new Expense());

        expense.setCategory(category);
        if (expense.getTransactions() == null) expense.setTransactions(new ArrayList<>());

        expense.getTransactions().add(new Transaction(amount, LocalDate.now(), description));

        return expenseMapper.toDTO(expenseRepository.save(expense));
    }

    // Calculate net profit or loss
    public String calculateNet(){
        double totalIncome = incomeRepository.findAll().stream()
                .filter(i -> i != null && i.getTransactions() != null)
                .flatMap(i -> i.getTransactions().stream())
                .mapToDouble(Transaction::getAmount).sum();

        double totalExpense = expenseRepository.findAll().stream()
                .filter(e -> e != null && e.getTransactions() != null)
                .flatMap(e -> e.getTransactions().stream())
                .mapToDouble(Transaction::getAmount).sum();

        double net = totalIncome - totalExpense;

        return net >= 0 ? "Net Profit: " + net : "Net Loss: " + Math.abs(net);
    }
}