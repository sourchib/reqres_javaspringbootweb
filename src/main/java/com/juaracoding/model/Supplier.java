package com.juaracoding.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * platform code : SLS
 * modul code : 02
 */
@Entity
@Table(name = "MstSupplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Nama",length = 50,unique = true)
    private String nama;
    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy=1L;
    @Column(name = "CreatedDate",nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;
    @Column(name = "ModifiedDate",insertable = false)
    private LocalDateTime modifiedDate;

    @ManyToMany(mappedBy = "supplierList")
    private List<Produk> produkList;

    public List<Produk> getProdukList() {
        return produkList;
    }

    public void setProdukList(List<Produk> produkList) {
        this.produkList = produkList;
    }

    //    @ManyToMany
//    @JsonBackReference
//    List<Produk> produkList ;

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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
