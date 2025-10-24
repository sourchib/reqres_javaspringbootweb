package com.juaracoding.service;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 24/10/2025 21:37
@Last Modified 24/10/2025 21:37
Version 1.0
*/

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class LogExportService {

    @Value("${logging.file.name:logs/app.log}")
    private String logFilePath;

    public Workbook exportLogToExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Application Logs");

        BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
        String line;
        int rowNum = 0;

        rowNum++;
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("No");
        header.createCell(1).setCellValue("Log Text");

        while ((line = reader.readLine()) != null) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(line);
        }

        reader.close();
        return workbook;
    }
}


