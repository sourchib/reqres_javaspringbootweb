package com.juaracoding.repo;

import com.juaracoding.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Long> {

    Page<Customer> findByNamaContainsIgnoreCase(Pageable page, String value);
    Page<Customer> findByAlamatContainsIgnoreCase(Pageable page, String value);
    Page<Customer> findByEmailContainsIgnoreCase(Pageable page, String value);
    Page<Customer> findByKodePosContainsIgnoreCase(Pageable page, String value);
    /** JPQL */
    @Query(value = "SELECT c FROM Customer c WHERE CAST(DATEDIFF(year,c.tanggalLahir,CURRENT_TIMESTAMP) AS STRING) LIKE CONCAT('%',?1,'%') ")
    Page<Customer> cariUmur(Pageable page, String value);

    @Query(value = "SELECT c FROM Customer c WHERE CAST(c.saldo AS STRING) LIKE CONCAT('%',?1,'%') ")
    Page<Customer> cariSaldo(Pageable page, String value);

    @Query(value = "SELECT c FROM Customer c WHERE CAST(c.saldo AS STRING) LIKE CONCAT('%',?1,'%') ")
    List<Customer> cariSaldo(String value);
    @Query(value = "SELECT c FROM Customer c WHERE CAST(DATEDIFF(year,c.tanggalLahir,CURRENT_TIMESTAMP) AS STRING) LIKE CONCAT('%',?1,'%') ")
    List<Customer> cariUmur(String value);
    List<Customer> findByNamaContainsIgnoreCase(String value);
    List<Customer> findByAlamatContainsIgnoreCase(String value);
    List<Customer> findByEmailContainsIgnoreCase(String value);
    List<Customer> findByKodePosContainsIgnoreCase(String value);
}