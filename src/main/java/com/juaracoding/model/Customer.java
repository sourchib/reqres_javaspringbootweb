package com.juaracoding.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MstCustomer",
        uniqueConstraints = @UniqueConstraint(name = "idx-combination", columnNames = {"nama","alamat"}),
indexes = @Index(name = "idx_email", columnList = "email"))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")//UPPER_SNAKE_CASE lower_snake_case PascalCase pascal_case
    private Long id;
    @Column(name = "Nama",length =70,nullable = false)
    private String nama;
    @Column(name = "Email",length =64,nullable = false,unique = true)
    private String email;
    @Column(name = "Alamat",nullable = false)//tidak perlu unique karena sudah masuk ke konfigurasi unique combination
    private String alamat;
    @Column(name = "TanggalLahir")
    private LocalDate tanggalLahir;

    @Transient
    private Integer umur;

    @Column(name = "Saldo" ,precision = 10,scale = 2)
    private BigDecimal saldo;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy;
    @Column(name = "CreatedDate",nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;
    @Column(name = "ModifiedDate",insertable = false)
    private LocalDateTime modifiedDate;

    /** change script when migration */
    @Column(name = "KodePos",length = 5,nullable = false ,columnDefinition = "CHAR(5)")
    private String kodePos;

}
