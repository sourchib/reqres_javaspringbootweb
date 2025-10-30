package com.juaracoding.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MstUser")
public class User {
    @Id
    private Long id;
}
