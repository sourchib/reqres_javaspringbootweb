package com.juaracoding.repo;

import com.juaracoding.model.KategoriProduk;
import com.juaracoding.model.Produk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KategoriProdukRepo extends JpaRepository<KategoriProduk,Long> {
    Page<KategoriProduk> findByNamaContainsIgnoreCase(Pageable page, String value);
    List<KategoriProduk> findByNamaContainsIgnoreCase(String value);
}
