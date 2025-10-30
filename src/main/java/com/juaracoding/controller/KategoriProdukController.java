package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.model.KategoriProduk;
import com.juaracoding.service.KategoriProdukService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kategoriproduk")
public class KategoriProdukController {

    @Autowired
    private KategoriProdukService kategoriProdukService;
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
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));//asc dan desc
        return kategoriProdukService.findAll(pageable,request);
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