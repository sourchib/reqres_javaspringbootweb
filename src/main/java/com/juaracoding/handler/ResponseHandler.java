package com.juaracoding.handler;
/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 29/10/2025 20:30
@Last Modified 29/10/2025 20:30
Version 1.0
*/
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/** Response Body untuk masalah Bisnis Logic dan Status Berhasil */
public class ResponseHandler {

    public ResponseEntity<Object> handleResponse(
            String message,
            HttpStatus status,
            Object data,
            Object errorCode,
            HttpServletRequest request
    ){

        Map<String,Object> m = new HashMap<>();
        m.put("message",message);
//        m.put("status",status.value());
        m.put("data",data==null?"":data);
        m.put("timestamp", Instant.now().toString());
        m.put("success",!status.isError());
        if(errorCode!=null){
            m.put("error_code",errorCode);
            m.put("path",request.getRequestURI());
//            m.put("path",request.getPathInfo());
        }
        return new ResponseEntity<>(m,status);
    }

    public ResponseEntity<Object> handleResponse(
            String message,
            HttpStatus status,
            Object data,
            Object errorCode,
            WebRequest request
    ){

        Map<String,Object> m = new HashMap<>();
        m.put("message",message);
//        m.put("status",status.value());
        m.put("data",data==null?"":data);
        m.put("timestamp", Instant.now().toString());
        m.put("success",!status.isError());
        if(errorCode!=null){
            m.put("error_code",errorCode);
            m.put("path",request.getContextPath());
        }
        return new ResponseEntity<>(m,status);
    }
}