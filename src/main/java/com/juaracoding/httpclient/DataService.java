package com.juaracoding.httpclient;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 29/10/2025 20:30
@Last Modified 29/10/2025 20:30
Version 1.0
*/

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient(name = "data-service",url = "${external1.url}"+"data")
public interface DataService {

    @GetMapping
    public String welcome();

    @GetMapping("/{id}/{nama}/{alamat}")
    public Map<String,Object> data1(@PathVariable String id,
                                    @PathVariable String nama,
                                    @PathVariable String alamat,
                                    @RequestParam String tanggalLahir,
                                    @RequestParam String email
    );

    @GetMapping("/{id}/{nama}/{alamat}")
    public Map<String,Object> data1(
            @RequestHeader String token,
            @PathVariable String id,
            @PathVariable String nama,
            @PathVariable String alamat,
            @RequestParam String tanggalLahir,
            @RequestParam String email
    );

    @PostMapping(value = "/mp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,Object> cobaData(
            @RequestPart MultipartFile file,
            @RequestParam String data
    );
}