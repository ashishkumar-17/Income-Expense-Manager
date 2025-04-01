package com.andi.manager.service;

import com.andi.manager.dto.InvoiceDTO;
import com.andi.manager.mapper.InvoiceMapper;
import com.andi.manager.model.Invoice;
import com.andi.manager.repository.InvoiceRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;


@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final ResourceLoader resourceLoader; // To load the logo file

    @Value("${company.logo.path}")
    private String defaultLogoPath; // Default logo from properties

    @Value("${company.name}")
    private String COMPANY_NAME;
    @Value("${company.address}")
    private String COMPANY_ADDRESS;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository,
                          InvoiceMapper invoiceMapper,
                          ResourceLoader resourceLoader){
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.resourceLoader = resourceLoader;
    }

    // create new invoice
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO){
        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        return invoiceMapper.toDTO(invoiceRepository.save(invoice));
    }


    // Generate pdf of the invoice
    public String generateInvoicePdf(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        String outputPath = "invoice_" + invoiceId + ".pdf";

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // Header with Logo and Title
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{1, 3});

            // Logo Cell
            String logoPath = invoice.getLogoPath() != null && !invoice.getLogoPath().isEmpty()
                    ? invoice.getLogoPath()
                    : resourceLoader.getResource(defaultLogoPath).getFile().getAbsolutePath();
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(100, 100);
            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(logoCell);

            // Title Cell
            PdfPCell titleCell = new PdfPCell(new Phrase("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(titleCell);

            document.add(headerTable);

            // Company Details
            document.add(new Paragraph("Company Name: " + COMPANY_NAME, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(new Paragraph("Address: " + COMPANY_ADDRESS, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(new Paragraph(" ")); // Spacer

            // Customer Details
            document.add(new Paragraph("Bill To:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            document.add(new Paragraph("Customer: " + invoice.getCustomerName(), FontFactory.getFont(FontFactory.HELVETICA, 12)));


            LocalDate localDate = invoice.getDate();
            document.add(new Paragraph("Date: " + localDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            document.add(new Paragraph(" ")); // Spacer

            // Items Table
            PdfPTable itemTable = new PdfPTable(4);
            itemTable.setWidthPercentage(100);
            itemTable.setWidths(new float[]{3, 1, 2, 2});

            // Table Headers
            itemTable.addCell(createCell("Item", true));
            itemTable.addCell(createCell("Qty", true));
            itemTable.addCell(createCell("Unit Price", true));
            itemTable.addCell(createCell("Subtotal", true));

            // Table Data
            for (Invoice.Item item : invoice.getItems()) {
                itemTable.addCell(createCell(item.getName(), false));
                itemTable.addCell(createCell(String.valueOf(item.getQuantity()), false));
                itemTable.addCell(createCell(String.format("₹%.2f", item.getPrice()), false));
                itemTable.addCell(createCell(String.format("₹%.2f", item.getSubtotal()), false));
            }

            document.add(itemTable);

            // Total Amount
            double total = invoice.getItems().stream().mapToDouble(Invoice.Item::getSubtotal).sum();
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{4, 2});
            totalTable.addCell(createCell("Total Amount:", true, Element.ALIGN_RIGHT));
            totalTable.addCell(createCell(String.format("₹%.2f", total), false));
            document.add(totalTable);

            // Footer
            document.add(new Paragraph(" "));
            Paragraph footer = new Paragraph("Thank you for your business!", FontFactory.getFont(FontFactory.HELVETICA, 10));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return outputPath;
        } catch (Exception e) {
            throw new RuntimeException("Error generating invoice: " + e.getMessage());
        }
    }

    private PdfPCell createCell(String content, boolean isHeader) {
        return createCell(content, isHeader, Element.ALIGN_LEFT);
    }

    private PdfPCell createCell(String content, boolean isHeader, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(FontFactory.HELVETICA, isHeader ? 12 : 10)));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        if (isHeader) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        }
        return cell;
    }
}