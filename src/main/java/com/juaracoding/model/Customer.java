package com.juaracoding.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

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
    private Long createdBy=1L;
    @Column(name = "CreatedDate",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;
    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy=1L;
    @UpdateTimestamp
    @Column(name = "ModifiedDate",insertable = false)
    private LocalDateTime modifiedDate;

    /** change script when migration */
    @Column(name = "KodePos",length = 5,nullable = false ,columnDefinition = "CHAR(5)")
    private String kodePos;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Integer getUmur() {
        return Period.between(tanggalLahir,LocalDate.now()).getYears();
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
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

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }
}
