package com.juaracoding.controller;


/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 23/10/2025 21:22
@Last Modified 23/10/2025 21:22
Version 1.0
*/

import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("data")
public class DataController {

    @GetMapping
    public String welcome(){
        return "Welcome brO";
    }

    @GetMapping("/{id}/{nama}/{alamat}")
    public Map<String,Object> data1(@PathVariable String id,
                                    @PathVariable String nama,
                                    @PathVariable String alamat,
                                    @RequestParam String tanggalLahir,
                                    @RequestParam String email
    ){
        Map<String,Object> m = new HashMap<>();
        m.put("id",id);
        m.put("nama",nama);
        m.put("alamat",alamat);
        m.put("tanggal_lahir",tanggalLahir);
        m.put("email",email);
        return m;
    }
    @PostMapping("/mp")
    public Map<String,Object> data1(@RequestParam MultipartFile file,
                                    @RequestParam String data
    ){
        Map<String,Object> m = new HashMap<>();
        m.put("file",file.getOriginalFilename());
        m.put("data",data+" - Lanjutan data dari server 2");
        List l = new ArrayList<>();
        Map<String,Object> mapList = new HashMap<>();
        mapList.put("id",2L);
        mapList.put("alamat","Bogor");
        l.add(mapList);
        mapList = new HashMap<>();
        mapList.put("id",3L);
        mapList.put("alamat","Jakarta");
        mapList.put("tanggalLahir","1995-12-12");
        l.add(mapList);
        m.put("listData",l);
        return m;
    }
}
