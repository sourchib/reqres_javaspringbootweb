package com.juaracoding.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kategoriproduk")
public class KategoriProdukController {

    // POST : localhost:8080/kategoriproduk
    @PostMapping
    public void save(){

    }

    // PUT : localhost:8080/kategoriproduk/1
    @PutMapping("/{id}")
    public void update(){

    }

    // DELETE : localhost:8080/kategoriproduk/1
    @DeleteMapping("/{id}")
    public void delete(){

    }

    // GET : localhost:8080/kategoriproduk
    @GetMapping
    public void findAll(){

    }

    // GET : localhost:8080/kategoriproduk/1
    @GetMapping("/{id}")
    public void findById(){

    }

    // GET : localhost:8080/kategoriproduk/asc/id/0?size=5&colum=nama&value=paul
    @GetMapping("/{sort}/{sort_by}/{page}")
    public void findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort_by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value
    ){

    }
}