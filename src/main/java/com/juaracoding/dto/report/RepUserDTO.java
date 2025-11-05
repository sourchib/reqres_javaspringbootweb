package com.juaracoding.dto.report;


public class RepUserDTO {

    private Long id;

    private String username;

//    private String email;

    private String noHp;

    private String namaLengkap;

//    private String alamat;


    private String tanggalLahir;

    private String namaAkses;

    private Integer umur;

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public Long getId() {
        return id;
    }

    public String getNamaAkses() {
        return namaAkses;
    }

    public void setNamaAkses(String namaAkses) {
        this.namaAkses = namaAkses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getEmail() {
//        return email;
//    }

//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }


    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

//    public String getAlamat() {
//        return alamat;
//    }

//    public void setAlamat(String alamat) {
//        this.alamat = alamat;
//    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}