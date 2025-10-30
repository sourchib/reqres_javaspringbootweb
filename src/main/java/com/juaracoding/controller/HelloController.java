package com.juaracoding.controller;

import com.juaracoding.coretan.Coba;
import com.juaracoding.coretan.Pelanggan;
import com.juaracoding.service.BPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class HelloController {

    @Autowired
    BPService bpService;

    @Value("${ayam.goreng}")
    private String makanan;

    @Autowired
    Coba coba;

    @Autowired
    Random random;

    @GetMapping("welcome")
    public String welcome(){
        coba.setNotif("Hello World");
        return coba.getNotif();
    }

    @GetMapping("data")
    public Map<String,Object> data(){
        Map<String, Object> m = new HashMap<>();
        m.put("id",1L);
        m.put("nama","Paul");
        m.put("timestamp", LocalDateTime.now());
        return m;
    }

    @GetMapping("kelas")
    public Object kelas(){
        coba.setNotif("Hallo");
        coba.setPesan("Morning");
        return coba;
    }

    @GetMapping("/pv/{id}/{nama_lengkap}")
    public Map<String,Object> pv(
            @PathVariable Long id,
            @PathVariable(value = "nama_lengkap") String namaLengkap
    ){
        Map<String,Object> m = new HashMap<>();
        m.put("id",id);
        m.put("nama_lengkap",namaLengkap);
        return m;
    }

    /**
    sample request : http://localhost:8080/pv/3/Paul%20Malau/1995-12-12?no_hp=08129128984&alamat_lengkap=bogor
     */
    @GetMapping("/pv/{id}/{nama_lengkap}/{tgl_lahir}")
    public Map<String,Object> pv(
            @PathVariable Long id,
            @PathVariable(value = "nama_lengkap") String namaLengkap,
            @PathVariable(value = "tgl_lahir") String tanggalLahir,
            @RequestParam(value = "no_hp") String noHp,
            @RequestParam(value = "alamat_lengkap") String alamatLengkap

    ){
        Map<String,Object> m = new HashMap<>();
        m.put("id",id);
        m.put("nama_lengkap",namaLengkap);
        m.put("tgl_lahir",tanggalLahir);
        m.put("no_hp",noHp);
        m.put("alamat_lengkap",alamatLengkap);
        return m;
    }

    /**
     sample request : http://localhost:8080/pv/3/Paul%20Malau/1995-12-12?no_hp=08129128984&alamat_lengkap=bogor
     */
    @PostMapping("/pv/{id}/{nama_lengkap}/{tgl_lahir}/{tambahan}")
    public Map<String,Object> pv(
            @PathVariable Long id,
            @PathVariable(value = "nama_lengkap") String namaLengkap,
            @PathVariable(value = "tgl_lahir") String tanggalLahir,
            @PathVariable String tambahan,
            @RequestParam(value = "no_hp") String noHp,
            @RequestParam(value = "alamat_lengkap") String alamatLengkap,
            @RequestBody Pelanggan pelanggan
            ){
        Map<String,Object> m = new HashMap<>();
        m.put("id",id);
        m.put("nama_lengkap",namaLengkap);
        m.put("tgl_lahir",tanggalLahir);
        m.put("no_hp",noHp);
        m.put("alamat_lengkap",alamatLengkap);
        m.put("jenis_kelamin",pelanggan.getJenisKelamin());
        m.put("kode_pos",pelanggan.getKodepos());
        m.put("tambahan",tambahan);
        m.put("cabang",pelanggan.getCabang());
        return m;
    }

    /**
     sample request : http://localhost:8080/pv/3/Paul%20Malau/1995-12-12?no_hp=08129128984&alamat_lengkap=bogor
     */
    @PostMapping("/mp/{id}/{nama_lengkap}/{tgl_lahir}/{tambahan}")
    public Map<String,Object> contohMultipart(
            @PathVariable Long id,
            @PathVariable(value = "nama_lengkap") String namaLengkap,
            @PathVariable(value = "tgl_lahir") String tanggalLahir,
            @PathVariable String tambahan,
            @RequestParam(value = "no_hp") String noHp,
            @RequestParam(value = "alamat_lengkap") String alamatLengkap,
            @RequestParam String nilai1,
            @RequestParam(value = "file_gambar") MultipartFile file
            ){
        Map<String,Object> m = new HashMap<>();
        m.put("id",id);
        m.put("nama_lengkap",namaLengkap);
        m.put("tgl_lahir",tanggalLahir);
        m.put("no_hp",noHp);
        m.put("alamat_lengkap",alamatLengkap);
        m.put("tambahan",tambahan);
        m.put("nama_file",file.getOriginalFilename());
        m.put("nilai1",nilai1);
        return m;
    }

    @PostMapping("/mparr/{id}/{nama_lengkap}/{tgl_lahir}/{tambahan}")
    public Map<String,Object> contohMultipartArray(
            @PathVariable Long id,
            @PathVariable(value = "nama_lengkap") String namaLengkap,
            @PathVariable(value = "tgl_lahir") String tanggalLahir,
            @PathVariable String tambahan,
            @RequestParam(value = "no_hp") String noHp,
            @RequestParam(value = "alamat_lengkap") String alamatLengkap,
            @RequestParam String [] nilai1,
            @RequestParam MultipartFile[] file
    ){
        int totalFile = file.length;

        Map<String,Object> m = new HashMap<>();
        if(totalFile>5 || totalFile<0){
            m.put("pesan_error","Gile lu");
            return m;
        }
        m.put("id",id);
        m.put("nama_lengkap",namaLengkap);
        m.put("tgl_lahir",tanggalLahir);
        m.put("no_hp",noHp);
        m.put("alamat_lengkap",alamatLengkap);
        m.put("tambahan",tambahan);
        m.put("nilai1",nilai1);
        for (int i = 0; i < file.length; i++) {
            m.put("file"+(i+1),file[i].getOriginalFilename());
        }
        return m;
    }

    @GetMapping("asink")
    public String testAsync(){
        bpService.asyncTest();
        return "OK";
    }
}