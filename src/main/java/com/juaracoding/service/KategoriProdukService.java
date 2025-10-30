package com.juaracoding.service;

import com.juaracoding.core.IService;
import com.juaracoding.dto.response.RespoProdukDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.KategoriProduk;
import com.juaracoding.model.Produk;
import com.juaracoding.repo.KategoriProdukRepo;
import com.juaracoding.util.LoggingFile;
import com.juaracoding.util.RequestCapture;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class KategoriProdukService implements IService<KategoriProduk> {

    @Autowired
    KategoriProdukRepo kategoriProdukRepo;

    @Override
    public ResponseEntity<Object> save(KategoriProduk kategoriProduk, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, KategoriProduk kategoriProduk, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<KategoriProduk> page=null;
        List<KategoriProduk> list=null;
        List<RespoProdukDTO> listDTO = null;
        Page<RespoProdukDTO> pageRespo=null;
        try {
            page = kategoriProdukRepo.findAll(pageable);
            if(page.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS01FV041",request);
            }
            list = page.getContent();
        }catch (Exception e){
            LoggingFile.logException("KategoriProdukService","findAll(Pageable pageable, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.BAD_REQUEST,null,"SLS01FE041",request);
        }

        return new ResponseHandler().handleResponse("Data Ditemukan",
                HttpStatus.OK,list,null,request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        return null;
    }
}
