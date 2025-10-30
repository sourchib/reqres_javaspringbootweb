package com.juaracoding.repo;

import com.juaracoding.model.Produk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//public interface ProdukRepo extends CrudRepository<Produk,Long> {
public interface ProdukRepo extends JpaRepository<Produk,Long> {

    /** SELECT p FROM Produk p WHERE p.nama */
    //produk join kategoriProduk join supplier
    /** SELECT p.* FROM MstProduk p WHERE Lower(p.nama) LIKE '%Lower(?)%'*/
    Page<Produk> findByNamaContainsIgnoreCase(Pageable page, String value);
    /** Query Buat di File */
    List<Produk> findByNamaContainsIgnoreCase(String value);

    /** SELECT p.* FROM MstProduk p WHERE Lower(p.nama) LIKE '%Lower(?)%' AND p.CreatedBy = ? */
    Page<Produk> findByNamaContainsIgnoreCaseAndCreatedBy(Pageable page, String value,String value2);

}