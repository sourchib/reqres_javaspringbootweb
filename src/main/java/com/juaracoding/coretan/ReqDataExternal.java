package com.juaracoding.coretan;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

public class ReqDataExternal {

    private Long id;
    @Pattern(regexp = "^[a-z\\s]{6,15}$")
    private String nama;
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\/.]{5,255}$")
    private String alamat;
    private String email;//RFC_5322
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String tanggalLahir;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
