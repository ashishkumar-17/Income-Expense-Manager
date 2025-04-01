package com.andi.manager.controller;

import com.andi.manager.dto.InvoiceDTO;
import com.andi.manager.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO){
        return ResponseEntity.ok(invoiceService.createInvoice(invoiceDTO));
    }

    @GetMapping("/{invoiceId}/generate")
    public ResponseEntity<String> generateInvoice(@PathVariable Long invoiceId){
        return ResponseEntity.ok(invoiceService.generateInvoicePdf(invoiceId));
    }
}