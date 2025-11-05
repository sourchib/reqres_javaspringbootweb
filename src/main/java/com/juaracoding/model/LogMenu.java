package com.juaracoding.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "LogMstMenu")
public class LogMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDMenu")
    private Long idMenu;

    @Column(name = "Nama", nullable = false, length = 50)
    private String nama;

    @Column(name = "Path", nullable = false, length = 50)
    private String path;

    @Column(name = "Deskripsi", nullable = false, length = 255)
    private String deskripsi;

    @Column(name = "IDGroupMenu")
    private Long idGroupMenu;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy=1L;

    @Column(name = "CreatedDate",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "Flag")
    private Character flag;

    public Character getFlag() {
        return flag;
    }

    public void setFlag(Character flag) {
        this.flag = flag;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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

    public Long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public Long getIdGroupMenu() {
        return idGroupMenu;
    }

    public void setIdGroupMenu(Long idGroupMenu) {
        this.idGroupMenu = idGroupMenu;
    }
}