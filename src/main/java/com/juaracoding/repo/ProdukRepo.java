package com.juaracoding.repo;

import com.juaracoding.model.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//public interface ProdukRepo extends CrudRepository<Produk,Long> {
public interface ProdukRepo extends JpaRepository<Produk,Long> {
}