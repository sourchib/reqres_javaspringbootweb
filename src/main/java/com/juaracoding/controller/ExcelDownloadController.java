package com.juaracoding.controller;

import com.juaracoding.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse; // Gunakan jakarta jika Spring Boot 3+
// import javax.servlet.http.HttpServletResponse; // Gunakan javax jika Spring Boot 2
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ExcelDownloadController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/download/excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {

        // 1. Mengatur Tipe Konten (MIME Type)
        // Ini memberi tahu browser bahwa file ini adalah file Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // 2. Mengatur Header "Content-Disposition"
        // Ini memberi tahu browser untuk "mendownload" file (attachment)
        // dan memberi nama file tersebut.
        String tgl = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=karyawan_" + tgl + ".xlsx";
        response.setHeader(headerKey, headerValue);
        response.setHeader("paul","paul value");

        // 3. Memanggil service untuk menulis file Excel ke output stream
        // response.getOutputStream() adalah stream yang terhubung ke browser klien
        excelService.generateExcelFile(response.getOutputStream());
    }
}