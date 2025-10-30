package com.juaracoding.dto.response;
/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 29/10/2025 20:30
@Last Modified 29/10/2025 20:30
Version 1.0
*/
public class RespoProdukDTO {

    private Long id;
    private String namaProduk;
    private Long idKatProd;
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

    public Long getIdKatProd() {
        return idKatProd;
    }

    public void setIdKatProd(Long idKatProd) {
        this.idKatProd = idKatProd;
    }

    public String getNamaKategoriProduk() {
        return namaKategoriProduk;
    }

    public void setNamaKategoriProduk(String namaKategoriProduk) {
        this.namaKategoriProduk = namaKategoriProduk;
    }
}
