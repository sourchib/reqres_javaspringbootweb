package com.juaracoding.service;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Khusus untuk XLSX
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class ExcelService {

    /**
     * Metode ini membuat Workbook POI dan menulisnya ke OutputStream.
     * OutputStream ini akan menjadi output HTTP response di controller.
     *
     * @param outputStream stream untuk menulis file Excel
     */
    public void generateExcelFile(OutputStream outputStream) throws IOException {
        // Gunakan try-with-resources untuk memastikan workbook ditutup secara otomatis
        try (Workbook workbook = new XSSFWorkbook()) {

            // 1. Membuat Sheet
            Sheet sheet = workbook.createSheet("Data Karyawan");

            // 2. Membuat Header Row
            Row headerRow = sheet.createRow(0);
//wrap
            // Menentukan style untuk header (opsional, tapi membuat rapi)
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Membuat sel untuk header
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("ID Karyawan");
            headerCell1.setCellStyle(headerStyle);

            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("Nama");
            headerCell2.setCellStyle(headerStyle);

            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue("Jabatan");
            headerCell3.setCellStyle(headerStyle);

            // 3. Menambahkan Data (Contoh data statis)
            // Dalam aplikasi nyata, ini bisa berasal dari database
            Row dataRow1 = sheet.createRow(1);
            dataRow1.createCell(0).setCellValue(101);
            dataRow1.createCell(1).setCellValue("Budi Santoso");
            dataRow1.createCell(2).setCellValue("Software Engineer");

            Row dataRow2 = sheet.createRow(2);
            dataRow2.createCell(0).setCellValue(102);
            dataRow2.createCell(1).setCellValue("Siti Aminah");
            dataRow2.createCell(2).setCellValue("Project Manager");

            // 4. Menyesuaikan lebar kolom (opsional)
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            // 5. Menulis workbook ke output stream
            // Ini adalah langkah kunci
            workbook.write(outputStream);
        }
        // Tidak perlu outputStream.close() di sini,
        // karena stream dikelola oleh Spring (HttpServletResponse)
    }
}