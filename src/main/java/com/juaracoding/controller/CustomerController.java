package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.validation.ValCustomerDTO;
import com.juaracoding.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValCustomerDTO valCustomerDTO, HttpServletRequest request){
        return customerService.save(customerService.mapDtoToEntity(valCustomerDTO),request);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,@Valid@RequestBody ValCustomerDTO valCustomerDTO, HttpServletRequest request){
        return customerService.update(id,customerService.mapDtoToEntity(valCustomerDTO),request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return customerService.delete(id,request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id, HttpServletRequest request){
        return customerService.findById(id,request);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));//asc dan desc
        return customerService.findAll(pageable,request);
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
        return customerService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadExcel(
            @RequestParam MultipartFile file,
            HttpServletRequest request){
        return customerService.uploadDataExcel(file,request);
    }

    private String sortByColumn(String sortBy){
        switch (sortBy){
            case "nama":sortBy="nama";break;
            case "alamat":sortBy="alamat";break;
            case "email":sortBy="email";break;
            case "kodePos":sortBy="kodePos";break;
            case "tanggalLahir":sortBy="tanggalLahir";break;
            case "saldo":sortBy="saldo";break;
            case "umur":sortBy="umur";break;
            default:sortBy="id";break;
        }
        return sortBy;
    }

    @GetMapping("/download-excel")
    public void downloadExcel(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        customerService.downloadReportExcel(column,value,request,response);
    }
    @GetMapping("/download-pdf")
    public void downloadPDF(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        customerService.downloadReportPDF(column,value,request,response);
    }
}
