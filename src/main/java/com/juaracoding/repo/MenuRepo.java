package com.juaracoding.repo;

import com.juaracoding.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface MenuRepo extends JpaRepository<Menu, Long> {

    public Page<Menu> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    public Page<Menu> findByPathContainsIgnoreCase(String nama, Pageable pageable);
    public Page<Menu> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);

    @Query("SELECT x FROM Menu x WHERE lower(x.groupMenu.nama) LIKE lower(concat('%',?1,'%') )")
    public Page<Menu> cariGroup(String nama, Pageable pageable);

    public List<Menu> findByNamaContainsIgnoreCase(String nama);
    public List<Menu> findByPathContainsIgnoreCase(String nama);
    public List<Menu> findByDeskripsiContainsIgnoreCase(String nama);
    @Query("SELECT x FROM Menu x WHERE lower(x.groupMenu.nama) LIKE lower(concat('%',?1,'%') )")
    public List<Menu> cariGroup(String nama);

    public Optional<Menu> findTop1ByOrderByIdDesc();
}