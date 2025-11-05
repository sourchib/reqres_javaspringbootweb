package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.rel.RelAksesDTO;
import com.juaracoding.dto.validation.ValUserDTO;
import com.juaracoding.service.UserService;
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

import java.time.LocalDate;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
//    @PostMapping
//    @PreAuthorize("hasAuthority('User')")
//    public ResponseEntity<Object> save(@Valid @RequestBody ValUserDTO valUserDTO,
//                                       HttpServletRequest request){
//
//        return userService.save(userService.mapToUser(valUserDTO),request);
//    }
//    @PostMapping
//    @PreAuthorize("hasAuthority('User')")
//    public ResponseEntity<Object> save(@Valid @RequestBody ValUserDTO valUserDTO,
//                                       @RequestParam MultipartFile file,
//                                       HttpServletRequest request){
//
//        return userService.save(userService.mapToUser(valUserDTO),file,request);
//    }
    @PostMapping
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> save(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("namaLengkap") String namaLengkap,
            @RequestParam("alamat") String alamat,
            @RequestParam("tanggalLahir") String tanggalLahir,
            @RequestParam("idAkses") String idAkses,
            @RequestParam("noHp") String noHp,
            @RequestParam("file") MultipartFile file,
           HttpServletRequest request){

        return userService.save(userService.mapToUser(mapToValUser(username,password,email,namaLengkap,alamat,tanggalLahir,idAkses,noHp)),file,request);
    }

    private ValUserDTO mapToValUser(String username,String password,
                                    String email,String namaLengkap,String alamat,
                                    String tanggalLahir,String idAkses,String noHp){
        ValUserDTO valUserDTO = new ValUserDTO();
        valUserDTO.setUsername(username);
        valUserDTO.setPassword(password);
        valUserDTO.setEmail(email);
        valUserDTO.setNoHp(noHp);
        valUserDTO.setNamaLengkap(namaLengkap);
        valUserDTO.setAlamat(alamat);
        RelAksesDTO relAksesDTO = new RelAksesDTO();
        relAksesDTO.setId(Long.parseLong(idAkses));
        valUserDTO.setAkses(relAksesDTO);
        valUserDTO.setTanggalLahir(LocalDate.parse(tanggalLahir));

        return valUserDTO;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> update(@Valid @RequestBody ValUserDTO valUserDTO,
                                       @PathVariable Long id,
                                       HttpServletRequest request){
        return userService.update(id, userService.mapToUser(valUserDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         HttpServletRequest request){
        return userService.delete(id,request);
    }

    /** defaultSearch
     * Ketika menu dibuka pertama kali, api yang di hit adalah api ini ....
     */
    @GetMapping
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return userService.findAll(pageable,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return userService.findById(id,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('User')")
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
        return userService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/upload-excel")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> uploadExcel(@RequestParam MultipartFile file, HttpServletRequest request){
        return userService.uploadDataExcel(file,request);
    }

    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('User')")
    public void downloadExcel(@RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        userService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('User')")
    public void downloadPdf(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        userService.downloadReportPDF(column,value,request,response);
    }

    private String sortColumn(String column){
        switch (column){
            case "namaLengkap":column="namaLengkap";break;
            case "username":column="username";break;
            case "alamat":column="alamat";break;
            case "tanggalLahir":column="tanggalLahir";break;
            case "noHp":column="noHp";break;
            case "email":column="email";break;
            case "namaAkses":column="akses.nama";break;
            case "umur":column="tanggalLahir";break;//karena umur diambil dari tanggal jadi dengan menggunakan kolom tanggal dapat di lakukan sorting nya
            default:column="id";break;
        }
        return column;
    }

    @PostMapping("/files/upload/{username}")
    public ResponseEntity<Object> uploadImage(
            @PathVariable String username,
            @RequestParam MultipartFile file, HttpServletRequest request){
        return userService.uploadImage(username,file,request);
    }
}