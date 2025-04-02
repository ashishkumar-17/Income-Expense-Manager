package com.manager.mapper;

import com.manager.dto.InvoiceDTO;
import com.manager.model.Invoice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvoiceMapper {

    // Entity to DTO
    public InvoiceDTO toDTO(Invoice invoice){
        if (invoice == null) return null;

        return new InvoiceDTO(invoice.getId(),
                invoice.getCustomerName(),
                invoice.getDate(),
                invoice.getItems(),
                invoice.getLogoPath());
    }

    // DTO to Entity
    public Invoice toEntity(InvoiceDTO invoiceDTO){
        if (invoiceDTO == null) return null;

        return new Invoice(invoiceDTO.getId(),
                invoiceDTO.getCustomerName(),
                invoiceDTO.getDate(),
                invoiceDTO.getItems(),
                invoiceDTO.getLogoPath());
    }

    // Entity list to DTO list
    public List<InvoiceDTO> toDTOList(List<Invoice> invoiceList){
        return invoiceList.stream()
                .map(this::toDTO)
                .toList();
    }

    // DTO list to Entity list
    public List<Invoice> toEntityList(List<InvoiceDTO> invoiceDTOList){
        return invoiceDTOList.stream()
                .map(this::toEntity)
                .toList();
    }
}