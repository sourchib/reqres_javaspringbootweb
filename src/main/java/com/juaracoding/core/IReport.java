package com.juaracoding.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IReport<T> {

    public ResponseEntity<Object> uploadDataExcel(MultipartFile file , HttpServletRequest request);//201-210
    public void downloadReportExcel(String column, String value , HttpServletRequest request, HttpServletResponse response);//211-220
    public void downloadReportPDF(String column, String value , HttpServletRequest request, HttpServletResponse response);//221-230
    public List<T> convertListWorkBookToListEntity(List<Map<String,String>> map,Long id);//231-240

}
