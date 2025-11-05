package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.validation.ValGroupMenuDTO;
import com.juaracoding.service.GroupMenuService;
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
@RequestMapping("group-menu")
public class GroupMenuController {


    @Autowired
    private GroupMenuService groupMenuService;

    @PostMapping
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValGroupMenuDTO valGroupMenuDTO,
                                       HttpServletRequest request){
        return groupMenuService.save(groupMenuService.mapToGroupMenu(valGroupMenuDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> update(@Valid @RequestBody ValGroupMenuDTO valGroupMenuDTO,
                                       @PathVariable Long id,
                                       HttpServletRequest request){
        return groupMenuService.update(id,groupMenuService.mapToGroupMenu(valGroupMenuDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         HttpServletRequest request){
        return groupMenuService.delete(id,request);
    }

    /** defaultSearch
     * Ketika menu dibuka pertama kali, api yang di hit adalah api ini ....
     */
    @GetMapping
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return groupMenuService.findAll(pageable,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return groupMenuService.findById(id,request);
    }

    /** api ketika user sudah melakukan interaksi di menu ini
     * searching, paging, sorting
     * localhost:8085/group-menu/kodok/cumi/0?size=10&column=nama&value=user
     */
    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Group-Menu')")
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
            case "desc":pageable = PageRequest.of(page,size, Sort.by(sortBy).descending());break;
            default:pageable = PageRequest.of(page,size, Sort.by(sortBy));break;
        }
        return groupMenuService.findByParam(pageable,column,value,request);
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
        return groupMenuService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/upload-excel")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> uploadExcel(@RequestParam MultipartFile file, HttpServletRequest request){
        return groupMenuService.uploadDataExcel(file,request);
    }

    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public void downloadExcel(@RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        groupMenuService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public void downloadPdf(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        groupMenuService.downloadReportPDF(column,value,request,response);
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