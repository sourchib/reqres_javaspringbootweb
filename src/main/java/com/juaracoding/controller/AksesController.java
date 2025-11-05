package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.validation.ValAksesDTO;
import com.juaracoding.service.AksesService;
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
@RequestMapping("akses")
public class AksesController {

    @Autowired
    private AksesService aksesService;

    @PostMapping
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValAksesDTO valAksesDTO,
                                       HttpServletRequest request){
        return aksesService.save(aksesService.mapToAkses(valAksesDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> update(@Valid @RequestBody ValAksesDTO valAksesDTO,
                                       @PathVariable Long id,
                                       HttpServletRequest request){
        return aksesService.update(id, aksesService.mapToAkses(valAksesDTO),request);
    }

//    @PutMapping("/v2/{id}")
//    @PreAuthorize("hasAuthority('Akses')")
//    public ResponseEntity<Object> updateV2(@Valid @RequestBody ValAksesDTO valAksesDTO,
//                                         @PathVariable Long id,
//                                         HttpServletRequest request){
//        return aksesService.update(id, aksesService.mapToAkses(valAksesDTO),request);
//    }

    /** RWX -> Read Write Execution */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         HttpServletRequest request){
        return aksesService.delete(id,request);
    }

    /** defaultSearch
     * Ketika akses dibuka pertama kali, api yang di hit adalah api ini ....
     */
    @GetMapping
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return aksesService.findAll(pageable,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return aksesService.findById(id,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Akses')")
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
        return aksesService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}/{q}")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable Integer page,
            @PathVariable Integer q,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request){
        Pageable pageable = null;
        sortBy = sortColumn(sortBy);
        switch (sort) {
            case "desc":pageable = PageRequest.of(page,size, Sort.by(sortBy).descending());break;
            default:pageable = PageRequest.of(page,size, Sort.by(sortBy));break;
        }
        return aksesService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/upload-excel")
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> uploadExcel(@RequestParam MultipartFile file, HttpServletRequest request){
        return aksesService.uploadDataExcel(file,request);
    }

    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('Akses')")
    public void downloadExcel(@RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        aksesService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('Akses')")
    public void downloadPdf(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        aksesService.downloadReportPDF(column,value,request,response);
    }

    private String sortColumn(String column){
        switch (column){
            case "nama":column="nama";break;
            case "deskripsi":column="deskripsi";break;
            default:column="id";break;
        }
        return column;
    }
}