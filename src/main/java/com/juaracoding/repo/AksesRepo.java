package com.juaracoding.repo;

import com.juaracoding.model.Akses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AksesRepo extends JpaRepository<Akses, Long> {

    /** SELECT * FROM MstAkses Where toLower(Nama) LIKE '%toLower(?)%'*/
    public Page<Akses> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    public Page<Akses> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);
//    public Page<Akses> findByDeskripsiContainsIgnoreCaseAndDiv(String nama,String divisi, Pageable pageable);

    public List<Akses> findByNamaContainsIgnoreCase(String nama);
    public List<Akses> findByDeskripsiContainsIgnoreCase(String nama);

    public Optional<Akses> findTop1ByOrderByIdDesc();
}