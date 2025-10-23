package com.juaracoding.coretan;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

public class Pelanggan {

    private String cabang;
    @JsonProperty("kode_pos")
    private String kodepos;
    @JsonProperty("jenis_kelamin")
    private String jenisKelamin;

    public String getCabang() {
        return cabang;
    }
    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getKodepos() {
        return kodepos;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}
