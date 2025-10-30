package com.juaracoding.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MstProduk")
public class Produk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Nama",length = 60,nullable = false,unique = true)
    private String nama;

    /** change script when migration */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDKategoriProduk",foreignKey = @ForeignKey(name = "fk-to-kategori",foreignKeyDefinition = "FOREIGN KEY ([IDKategoriProduk]) REFERENCES [projectz].[MstKategori] ([ID]) ON UPDATE CASCADE ON DELETE CASCADE"))
    private KategoriProduk kategoriProduk;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy=1L;

    @Column(name = "CreatedDate",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;

    @UpdateTimestamp
    @Column(name = "ModifiedDate",insertable = false)
    private LocalDateTime modifiedDate;

    @ManyToMany
    @JoinTable(name = "ProdukSupplier",
            uniqueConstraints = @UniqueConstraint(name = "unq-produk-supplier",columnNames = {"IDProduk","IDSupplier"}),
            joinColumns = @JoinColumn(name = "IDProduk",foreignKey = @ForeignKey(name = "fk-map-produk")),
            inverseJoinColumns = @JoinColumn(name = "IDSupplier",foreignKey = @ForeignKey(name = "fk-map-supplier"))
    )
    private List<Supplier> supplierList;

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

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

    public KategoriProduk getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(KategoriProduk kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
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
