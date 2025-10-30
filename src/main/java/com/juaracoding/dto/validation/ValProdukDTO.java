package com.juaracoding.dto.validation;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.dto.RelationDTO;
import com.juaracoding.model.KategoriProduk;
import com.juaracoding.model.Supplier;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class ValProdukDTO {

    @Pattern(regexp = "^[\\w\\s]{4,20}$",message = "Format nama tidak sesuai")
    private String nama;

    @NotNull
    @JsonProperty("kategori")
    private RelationDTO relationDTO;

    @NotNull
    @JsonProperty("list_supplier")
    private List<RelationDTO> relationDTOList;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public RelationDTO getRelationDTO() {
        return relationDTO;
    }

    public void setRelationDTO(RelationDTO relationDTO) {
        this.relationDTO = relationDTO;
    }

    public List<RelationDTO> getRelationDTOList() {
        return relationDTOList;
    }

    public void setRelationDTOList(List<RelationDTO> relationDTOList) {
        this.relationDTOList = relationDTOList;
    }
}
