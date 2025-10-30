package com.juaracoding.handler;

import com.juaracoding.util.LoggingFile;
import com.juaracoding.util.RequestCapture;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 */
@RestControllerAdvice
@Configuration
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

    private static final String CLASS_NAME = "";

    public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
        LoggingFile.logException(CLASS_NAME,"handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, HttpServletRequest request) "+ RequestCapture.allRequest(request),ex);
        return new ResponseHandler().handleResponse("Terjadi Kesalahan Di Server",status,null,"X05999",request);
    }
}