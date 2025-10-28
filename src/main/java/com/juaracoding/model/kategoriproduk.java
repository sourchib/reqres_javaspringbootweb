package com.juaracoding.model;
/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 28/10/2025 19:08
@Last Modified 28/10/2025 19:08
Version 1.0
*/

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MstKategori")
public class kategoriproduk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Nama",length = 60,nullable = false,unique = true)
    private String nama;

//    @OneToMany
//    private List<Produk> produk;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy;
    @Column(name = "CreatedDate",nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;
    @Column(name = "ModifiedDate",insertable = false)
    private LocalDateTime modifiedDate;
}


