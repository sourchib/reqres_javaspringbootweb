package com.juaracoding.model;


import jakarta.persistence.*;

@Entity
@Table(name = "TrxCart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToOne
    @JoinColumn(name = "IDUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "IDProduk")
    private Produk produk;

    @Column(name = "JumlahBarang")
    private Integer jumlahBarang;

}
