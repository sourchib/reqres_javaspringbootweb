package com.juaracoding.dto.validation;


import jakarta.validation.constraints.Pattern;

public class ValSupplierDTO {
    @Pattern(regexp = "^[\\w\\s]{10,30}$",message = "Format nama tidak sesuai")
    private String nama;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
