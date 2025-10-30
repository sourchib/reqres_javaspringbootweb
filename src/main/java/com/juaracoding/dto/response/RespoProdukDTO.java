package com.juaracoding.dto.response;

public class RespoProdukDTO {

    private Long id;
    private String namaProduk;
//    private Long idKatProd;
    private String namaKategoriProduk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

//    public Long getIdKatProd() {
//        return idKatProd;
//    }
//
//    public void setIdKatProd(Long idKatProd) {
//        this.idKatProd = idKatProd;
//    }

    public String getNamaKategoriProduk() {
        return namaKategoriProduk;
    }

    public void setNamaKategoriProduk(String namaKategoriProduk) {
        this.namaKategoriProduk = namaKategoriProduk;
    }
}
