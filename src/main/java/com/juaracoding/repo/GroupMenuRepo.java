package com.juaracoding.repo;

import com.juaracoding.model.GroupMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface GroupMenuRepo extends JpaRepository<GroupMenu, Long> {

    /** SELECT * FROM MstGroupMenu WHERE toLower('variable_') LIKE toLower('%?%') */
    public Page<GroupMenu> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    public Page<GroupMenu> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);
//    public Page<GroupMenu> findByDeskripsiContainsIgnoreCaseAndStatusAndKategori(String nama,String status,String kategori, Pageable pageable);

    public List<GroupMenu> findByNamaContainsIgnoreCase(String nama);
    public List<GroupMenu> findByDeskripsiContainsIgnoreCase(String nama);
    public Optional<GroupMenu> findTop1ByOrderByIdDesc();
}