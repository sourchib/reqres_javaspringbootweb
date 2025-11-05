package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.validation.ValKategoriProdukDTO;
import com.juaracoding.service.KategoriProdukService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("kategoriproduk")
public class KategoriProdukController {

    @Autowired
    private KategoriProdukService kategoriprodukService;

//    @PostMapping("/save")
//    public ResponseEntity<Object> saveOpen(@Valid @RequestBody ValKategoriProdukDTO valProdukDTO, HttpServletRequest request){
//        return kategoriprodukService.save(kategoriprodukService.mapDtoToEntity(valProdukDTO),request);
//    }
    @PostMapping
    @PreAuthorize("hasAuthority('Kategori')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValKategoriProdukDTO valProdukDTO, HttpServletRequest request){
        return kategoriprodukService.save(kategoriprodukService.mapDtoToEntity(valProdukDTO),request);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,@Valid@RequestBody ValKategoriProdukDTO valProdukDTO, HttpServletRequest request){
        return kategoriprodukService.update(id,kategoriprodukService.mapDtoToEntity(valProdukDTO),request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return kategoriprodukService.delete(id,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Kategori')")
    public ResponseEntity<Object> findById(@PathVariable Long id, HttpServletRequest request){
        return kategoriprodukService.findById(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Kategori')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));//asc dan desc
        return kategoriprodukService.findAll(pageable,request);
    }

    @GetMapping("/{sort}/{sort_by}/{page}")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort_by") String sortBy,
            @PathVariable Integer page,
            @RequestParam String column,
            @RequestParam String value,
            @RequestParam Integer size,
            HttpServletRequest request){
        Pageable pageable = null;
        sortBy = sortByColumn(sortBy);
        if(sort.equals("asc")){
            pageable = PageRequest.of(page,size, Sort.by(sortBy));
        }else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }
        return kategoriprodukService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadExcel(
            @RequestParam MultipartFile file,
            HttpServletRequest request){
        return kategoriprodukService.uploadDataExcel(file,request);
    }

    private String sortByColumn(String sortBy){
        switch (sortBy){
            case "nama":sortBy="nama";break;
            default:sortBy="id";break;
        }
        return sortBy;
    }

    @GetMapping("/download-excel")
    public void downloadExcel(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        kategoriprodukService.downloadReportExcel(column,value,request,response);
    }
    @GetMapping("/download-pdf")
    public void downloadPDF(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        kategoriprodukService.downloadReportPDF(column,value,request,response);
    }
}
