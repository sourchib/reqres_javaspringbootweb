package com.juaracoding.controller;

import com.juaracoding.model.Product;
import com.juaracoding.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/download/invoice")
    public ResponseEntity<byte[]> downloadInvoice() {
        try {
            // --- 1. Siapkan Data (Biasanya data ini diambil dari Database) ---
            List<Product> products = Arrays.asList(
                    new Product("Laptop Pro", 1, 15000000.0),
                    new Product("Mouse Wireless", 2, 250000.0),
                    new Product("Keyboard Mekanikal", 1, 750000.0)
            );

            double grandTotal = products.stream().mapToDouble(Product::getTotal).sum();

            // Masukkan data ke dalam Map untuk dikirim ke template
            Map<String, Object> data = new HashMap<>();
            data.put("customerName", "Budi Santoso");
            data.put("products", products);
            data.put("grandTotal", grandTotal);

            // --- 2. Panggil Service untuk Generate PDF ---
            byte[] pdfBytes = pdfService.generatePdf("invoice.html", data);

            // --- 3. Atur HTTP Headers untuk Download ---
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            // Ini memberitahu browser untuk men-download file dengan nama "invoice.pdf"
            headers.setContentDispositionFormData("filename", "invoice.pdf");

            headers.setContentLength(pdfBytes.length);
            headers.set("Paul","Paul Value");

            // --- 4. Kembalikan PDF sebagai ResponseEntity ---
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            // Tangani error jika terjadi
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}