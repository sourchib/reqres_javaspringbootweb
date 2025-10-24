package com.juaracoding.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;


public class JavaFilterz implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inisialisasi filter jika diperlukan
        System.out.println("Filter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Logika sebelum memproses request
        System.out.println("Request URI: " + httpRequest.getRequestURI());
        System.out.println("Request Method: " + httpRequest.getMethod());
        String strContentType = request.getContentType()==null?"":request.getContentType();
        if(!strContentType.startsWith("multipart/form-data") || "".equals(strContentType)){
            request = new MyHttpServletRequestWrapper(httpRequest);
        }
        // Melanjutkan ke filter chain
        chain.doFilter(request, response);

        // Logika setelah memproses request
        System.out.println("Response sent");
    }

    @Override
    public void destroy() {
        // Logika cleanup jika diperlukan
        System.out.println("Filter destroyed");
    }

}
