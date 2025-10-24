package com.juaracoding.httpclient;


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
