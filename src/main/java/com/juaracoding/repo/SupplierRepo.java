package com.juaracoding.repo;

import com.juaracoding.model.KategoriProduk;
import com.juaracoding.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepo extends JpaRepository<Supplier,Long> {
    Page<Supplier> findByNamaContainsIgnoreCase(Pageable page, String value);
    List<Supplier> findByNamaContainsIgnoreCase(String value);
    public Optional<Supplier> findTop1ByOrderByIdDesc();
}
