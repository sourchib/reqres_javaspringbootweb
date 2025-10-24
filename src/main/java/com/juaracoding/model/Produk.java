package com.juaracoding.model;
/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 28/10/2025 18:38
@Last Modified 28/10/2025 18:38
Version 1.0
*/

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "IDKategoriProduk",foreignKey = @ForeignKey(name = "fk-to-kategori",foreignKeyDefinition = "FOREIGN KEY ([IDKategoriProduk]) REFERENCES [projectz].[MstKategori] ([ID]) ON UPDATE SET NULL"))
    private KategoriProduk kategoriProduk;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy=1L;
    @Column(name = "CreatedDate",nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;
    @Column(name = "ModifiedDate",insertable = false)
    private LocalDateTime modifiedDate;
}


