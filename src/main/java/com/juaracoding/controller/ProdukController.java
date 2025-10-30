package com.juaracoding.controller;

import com.juaracoding.dto.validation.ValProdukDTO;
import com.juaracoding.model.Produk;
import com.juaracoding.service.ProdukService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Object save(@Valid @RequestBody ValProdukDTO valProdukDTO, HttpServletRequest request){
        return produkService.save(produkService.mapDtoToEntity(valProdukDTO),request);
    }
    @PutMapping("/{id}")
    public Object update(@PathVariable Long id,@Valid@RequestBody ValProdukDTO valProdukDTO, HttpServletRequest request){
        return produkService.update(id,produkService.mapDtoToEntity(valProdukDTO),request);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Long id, HttpServletRequest request){
        return produkService.delete(id,request);
    }
}
