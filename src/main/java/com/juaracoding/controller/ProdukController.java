package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.validation.ValProdukDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.service.ProdukService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("produk")
public class ProdukController {

    @Autowired
    private ProdukService produkService;

    /**
     * {
     * 	"nama":"Parutan",
     * 	"kategori":{
     * 		"id":3
     * 	    },
     * 	"list_supplier":[
     *        {
     * 			"id":1
     *        },
     *        {
     * 			"id":2
     *        },
     *        {
     * 			"id":3
     *        }
     * 	]
     * }
     * @param valProdukDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValProdukDTO valProdukDTO, HttpServletRequest request){
        return produkService.save(produkService.mapDtoToEntity(valProdukDTO),request);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,@Valid@RequestBody ValProdukDTO valProdukDTO, HttpServletRequest request){
        return produkService.update(id,produkService.mapDtoToEntity(valProdukDTO),request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request){
        return produkService.delete(id,request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id, HttpServletRequest request){
        return produkService.findById(id,request);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));//asc dan desc
        return produkService.findAll(pageable,request);
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
//        if(sortBy.equals("error")){
//            return new ResponseHandler().handleResponse("")
//        }
        if(sort.equals("asc")){
            pageable = PageRequest.of(page,size, Sort.by(sortBy));
        }else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }
        return produkService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadExcel(
            @RequestParam MultipartFile file,
            HttpServletRequest request){
        return produkService.uploadDataExcel(file,request);
    }

    private String sortByColumn(String sortBy){
        switch (sortBy){
            case "nama":sortBy="nama";break;
//            case "id":sortBy="id";break;
            default:sortBy="id";break;
        }
        return sortBy;
    }
}
