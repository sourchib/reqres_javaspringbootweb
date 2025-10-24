package com.juaracoding.controller;
/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 24/10/2025 21:38
@Last Modified 24/10/2025 21:38
Version 1.0
*/
import com.juaracoding.service.LogExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LogController {

    @Autowired
    private LogExportService logExportService;

    @GetMapping("/export/logs")
    public void exportLogs(HttpServletResponse response) throws IOException {
        Workbook workbook = logExportService.exportLogToExcel();

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=logs.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}



