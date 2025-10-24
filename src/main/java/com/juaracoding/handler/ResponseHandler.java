package com.juaracoding.handler;

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