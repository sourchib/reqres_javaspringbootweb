package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.validation.ValMenuDTO;
import com.juaracoding.service.MenuService;
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
@RequestMapping("menu")
public class MenuController {


    @Autowired
    private MenuService menuService;


    @PostMapping
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValMenuDTO valMenuDTO,
                                       HttpServletRequest request){
        return menuService.save(menuService.mapToMenu(valMenuDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> update(@Valid @RequestBody ValMenuDTO valMenuDTO,
                                       @PathVariable Long id,
                                       HttpServletRequest request){
        return menuService.update(id, menuService.mapToMenu(valMenuDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         HttpServletRequest request){
        return menuService.delete(id,request);
    }

    /** defaultSearch
     * Ketika menu dibuka pertama kali, api yang di hit adalah api ini ....
     */
    @GetMapping
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return menuService.findAll(pageable,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return menuService.findById(id,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request){
        Pageable pageable = null;
        sortBy = sortColumn(sortBy);
        switch (sort) {
            case "desc":pageable = PageRequest.of(page,size, Sort.by("id").descending());break;
            default:pageable = PageRequest.of(page,size, Sort.by("id"));break;
        }
        return menuService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/upload-excel")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> uploadExcel(@RequestParam MultipartFile file, HttpServletRequest request){
        return menuService.uploadDataExcel(file,request);
    }

    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('Menu')")
    public void downloadExcel(@RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        menuService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('Menu')")
    public void downloadPdf(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        menuService.downloadReportPDF(column,value,request,response);
    }

    private String sortColumn(String column){
        switch (column){
            case "nama":column="nama";break;
            case "deskripsi":column="deskripsi";break;
            case "path":column="path";break;
            case "namaGroupMenu":column="groupMenu.nama";break;
            default:column="id";break;
        }
        return column;
    }
}