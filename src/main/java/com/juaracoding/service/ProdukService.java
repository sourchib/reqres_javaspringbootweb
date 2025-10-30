package com.juaracoding.service;

import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.RelationDTO;
import com.juaracoding.dto.response.RespoProdukDTO;
import com.juaracoding.dto.response.RespoSupplierDTO;
import com.juaracoding.dto.validation.ValProdukDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.KategoriProduk;
import com.juaracoding.model.Produk;
import com.juaracoding.model.Supplier;
import com.juaracoding.repo.ProdukRepo;
import com.juaracoding.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * platform code : SLS
 * modul code : 03
 */
@Service
@Transactional
public class ProdukService implements IService<Produk>, IReport<Produk> {

    @Autowired
    private ProdukRepo produkRepo;

    @Autowired
    private TransformPagination tp;

    private ModelMapper modelMapper = new ModelMapper();
    private StringBuilder sBuild = new StringBuilder();
    private static final String className = "ProdukService";

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;


    @Override//001-010
    public ResponseEntity<Object> save(Produk produk, HttpServletRequest request) {

        if(produk==null){
            return new ResponseHandler().handleResponse("Data Gagal Disimpan",
                    HttpStatus.BAD_REQUEST,null,"SLS03FV001",request);
        }
        try{
            produkRepo.save(produk);
        }catch (Exception e){
            LoggingFile.logException(className,"save(Produk produk, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Disimpan",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE001",request);
        }
        return new ResponseHandler().handleResponse("Data Berhasil Disimpan",
                HttpStatus.CREATED,null,null,request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Produk produk, HttpServletRequest request) {
        if(produk==null){
            return new ResponseHandler().handleResponse("Data Gagal Diubah",
                    HttpStatus.BAD_REQUEST,null,"SLS03FV011",request);
        }
        try{
            Optional<Produk> optionalProduk = produkRepo.findById(id);
            if(optionalProduk.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS03FV012",request);
            }
            Produk nextProduk = optionalProduk.get();
            nextProduk.setKategoriProduk(produk.getKategoriProduk());
            nextProduk.setNama(produk.getNama());
            nextProduk.setSupplierList(produk.getSupplierList());
            nextProduk.setModifiedBy(1L);

        }catch (Exception e){
            LoggingFile.logException(className,"update(Long id,Produk produk, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Diubah",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE011",request);
        }
        return new ResponseHandler().handleResponse("Data Berhasil Diubah",
                HttpStatus.OK,null,null,request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        if(id==null){
            return new ResponseHandler().handleResponse("Data Gagal Dihapus",
                    HttpStatus.BAD_REQUEST,null,"SLS03FV021",request);
        }
        try{
            Optional<Produk> optionalProduk = produkRepo.findById(id);
            if(optionalProduk.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS03FV022",request);
            }
            produkRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.logException(className,"delete(Long id, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Dihapus",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE021",request);
        }
        return new ResponseHandler().handleResponse("Data Berhasil Dihapus",
                HttpStatus.OK,null,null,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Produk nextProduk =null;
        RespoProdukDTO respoProdukDTO = null;
        if(id==null){
            return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                    HttpStatus.BAD_REQUEST,null,"SLS03FV031",request);
        }
        try{
            Optional<Produk> optionalProduk = produkRepo.findById(id);
            if(optionalProduk.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS03FV032",request);
            }
            nextProduk = optionalProduk.get();
            respoProdukDTO = entityToDTO(nextProduk);
        }catch (Exception e){
            LoggingFile.logException(className,"findById(Long id, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Diubah",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE031",request);
        }
        return new ResponseHandler().handleResponse("Data Ditemukan",
                HttpStatus.OK,respoProdukDTO,null,request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Produk> page=null;
        List<RespoProdukDTO> listDTO = null;
        Page<RespoProdukDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            page = produkRepo.findAll(pageable);
            if(page.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS03FV041",request);
            }
//            listDTO = entityToDTO(page.getContent());//List<Produk> -> List<RespoProduk>
            listDTO = entityToDTO(page.getContent());//List<Produk> -> List<RespoProduk>
            data = tp.transformPagination(listDTO,page,"id","");
        }catch (Exception e){
            LoggingFile.logException(className,"findAll(Pageable pageable, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE041",request);
        }

        return new ResponseHandler().handleResponse("Data Ditemukan",
                HttpStatus.OK,data,null,request);
    }

//    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
//        Page<Produk> page=null;
//        List<RespoProdukDTO> listDTO = null;
//        Page<RespoProdukDTO> pageRespo=null;
//        Map<String,Object> data = null;
//        try {
//            page = produkRepo.findAll(pageable);
//            if(page.isEmpty()){
//                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
//                        HttpStatus.NOT_FOUND,null,"SLS03FV041",request);
//            }
//            listDTO = entityToDTO(page.getContent());//List<Produk> -> List<RespoProduk>
//            data = tp.transformPagination(listDTO,page,"id","");
//        }catch (Exception e){
//            LoggingFile.logException(className,"findAll(Pageable pageable, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
//            return new ResponseHandler().handleResponse("Internal Server Error",
//                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE041",request);
//        }
//
//        return new ResponseHandler().handleResponse("Data Ditemukan",
//                HttpStatus.OK,data,null,request);
//    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        Page<Produk> page=null;
        List<RespoProdukDTO> listDTO = null;
        Page<RespoProdukDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column){
                case "nama":page = produkRepo.findByNamaContainsIgnoreCase(pageable,value);break;
                default:page = produkRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS03FV051",request);
            }
            listDTO = entityToDTO(page.getContent());//List<Produk> -> List<RespoProduk>
            data = tp.transformPagination(listDTO,page,column,value);
        }catch (Exception e){
            LoggingFile.logException(className,"findByParam(Pageable pageable, String column, String value, HttpServletRequest request)  "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE051",request);
        }

        return new ResponseHandler().handleResponse("Data Ditemukan",
                HttpStatus.OK,data,null,request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try{
            if(!ExcelReader.hasWorkBookFormat(file)){
                return new ResponseHandler().handleResponse("Tidak Mendukung File Tersebut",
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE,null,"SLS03FV061",request);
            }
            List lt = new ExcelReader(file.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return new ResponseHandler().handleResponse("File Kosong",
                        HttpStatus.BAD_REQUEST,null,"SLS03FV062",request);
            }
            produkRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        }catch (Exception e){
            LoggingFile.logException(className,"uploadDataExcel(MultipartFile file, HttpServletRequest request)   "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE061",request);
        }
        return new ResponseHandler().handleResponse("Upload File Berhasil",
                HttpStatus.CREATED,null,null,request);
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Produk> list = null;
        List<RespoProdukDTO> listDTO = null;
        Page<RespoProdukDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column){
                case "nama":list = produkRepo.findByNamaContainsIgnoreCase(value);break;
                default:list = produkRepo.findAll();
            }
            if(list.isEmpty()){
                GlobalResponse.manualResponse(response, new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS03FV071",request));
                return;
            }
            listDTO = entityToDTO(list);//List<Produk> -> List<RespoProduk>

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=akses_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//akses_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RespoProdukDTO());
//            ("id",null);-> ID ==> map.get("id")
//            ("namaProduk",null);-> NAMA PRODUK
//            ("namaKategoriProduk",null);-> NAMA KATEGORI PRODUK
            List<String> listTemp = new ArrayList<>();
            for (Map.Entry<String,Object> entry : map.entrySet()) {
                listTemp.add(entry.getKey());
            }
            int intListTemp = listTemp.size();
            String [] headerArr = new String[intListTemp];//kolom judul di excel
            String [] loopDataArr = new String[intListTemp];//kolom judul java reflection

            for (int i = 0; i < intListTemp; i++) {
                headerArr[i] = GlobalFunction.camelToStandard(listTemp.get(i));
                loopDataArr[i] = listTemp.get(i);
            }
            int intListDTOSize = listDTO.size();
            String [][] strBody = new String[intListDTOSize][intListTemp];
            for (int i = 0; i < intListDTOSize; i++) {
                map = GlobalFunction.convertClassToMap(listDTO.get(i));
                for (int j = 0; j < intListTemp; j++) {
                    strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
                }
            }
            new ExcelWriter(strBody,headerArr,"sheet-1",response);
//            for (int i = 0; i < intListDTOSize; i++) {
//               strBody[i][0] = String.valueOf(listDTO.get(i).getId());
//               strBody[i][1] = String.valueOf(listDTO.get(i).getNamaProduk());
//               strBody[i][2] = String.valueOf(listDTO.get(i).getNamaKategoriProduk());
//            }
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
            LoggingFile.logException(className,"downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) "+ RequestCapture.allRequest(request),e);
            GlobalResponse.manualResponse(response, new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE071",request));
            return;
        }
    }

    @Override
    public void downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Produk> list = null;
        List<RespoProdukDTO> listDTO = null;
        Page<RespoProdukDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column) {
                case "nama":
                    list = produkRepo.findByNamaContainsIgnoreCase(value);
                    break;
                default:
                    list = produkRepo.findAll();
            }
            if (list.isEmpty()) {
                GlobalResponse.manualResponse(response, new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND, null, "SLS03FV081", request));
                return;
            }
            listDTO = entityToDTO(list);//List<Produk> -> List<RespoProduk>
            int intRepAksesDTOSize = listDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RespoProdukDTO());
            List<String> listTemp = new ArrayList<>();
            List<String> listHelper = new ArrayList<>();
            for (Map.Entry<String,Object> m:mapColumnName.entrySet()) {
                listTemp.add(GlobalFunction.camelToStandard(m.getKey()));
                listHelper.add(m.getKey());
            }

            Map<String,Object> mapTemp = null;
            List<Map<String,Object>> listMap = new ArrayList<>();
            for (int i = 0; i < intRepAksesDTOSize; i++) {
                mapTemp = GlobalFunction.convertClassToMap(listDTO.get(i));
                listMap.add(mapTemp);
            }
            mapResponse.put("title","REPORT DATA PRODUK");
            mapResponse.put("listKolom",listTemp);
            mapResponse.put("listHelper",listHelper);
            mapResponse.put("listContent",listMap);
            mapResponse.put("totalData",intRepAksesDTOSize);
            mapResponse.put("username","Paul");
            mapResponse.put("timestamp", LocalDateTime.now());
            context.setVariables(mapResponse);
            strHtml = springTemplateEngine.process("global-report",context);
            pdfGenerator.htmlToPdf(strHtml,"akses",response);
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
            LoggingFile.logException(className,"downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) "+ RequestCapture.allRequest(request),e);
            GlobalResponse.manualResponse(response, new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS03FE081",request));
        }
    }

    @Override
    public List<Produk> convertListWorkBookToListEntity(List<Map<String, String>> listData, Long userId) {
        List<Produk> l = new ArrayList<>();
        String value ="";
        for (Map<String,String> m:listData){
            Produk p = new Produk();
            p.setNama(m.get("Nama-Produk"));//nama kolom di file excel
//            value = m.get("Nama-Produk");
//            if(!GlobalFunction.checkValue(value,"^[\\w\\s\\.]{1,30}$")){
//                return ;
//            }
//            p.setNama(value);
            p.setCreatedBy(userId);
            l.add(p);
        }
        return l;
    }

    public Produk mapDtoToEntity(ValProdukDTO valProdukDTO){
        Produk produk = new Produk();
        produk.setNama(valProdukDTO.getNama());
        KategoriProduk k = new KategoriProduk();
        k.setId(valProdukDTO.getRelationDTO().getId());
        produk.setKategoriProduk(k);
        List<Supplier> l = new ArrayList<>();
        for (RelationDTO r:
             valProdukDTO.getRelationDTOList()) {
            Supplier s = new Supplier();
            s.setId(r.getId());
            l.add(s);
        }
        produk.setSupplierList(l);
        return produk;
    }
    public RespoProdukDTO entityToDTO(Produk produk){
        RespoProdukDTO r = new RespoProdukDTO();
        r.setId(produk.getId());
        r.setNamaProduk(produk.getNama());
        r.setNamaKategoriProduk(produk.getKategoriProduk().getNama());
//        r.setIdKatProd(produk.getKategoriProduk().getId());
        return r;
    }

    public List<RespoProdukDTO> entityToDTO(List<Produk> produk){
        List<RespoProdukDTO> l = new ArrayList<>();
        for (int i = 0; i < produk.size(); i++) {
            RespoProdukDTO r = new RespoProdukDTO();
            r.setId(produk.get(i).getId());
            r.setNamaProduk(produk.get(i).getNama());
            r.setNamaKategoriProduk(produk.get(i).getKategoriProduk().getNama());
//            List<RespoSupplierDTO> sList = new ArrayList<>();
//            List<Supplier> ls = produk.get(i).getSupplierList();
//            for (int j = 0; j < ls.size(); j++) {
//                RespoSupplierDTO rsd = new RespoSupplierDTO();
//                rsd.setId(ls.get(i).getId());
//                rsd.setNama(ls.get(i).getNama());
//                sList.add(rsd);
//            }
//            r.setSupplierList(sList);
            r.setIdKatProd(produk.get(i).getKategoriProduk().getId());
            l.add(r);
        }
        return l;
    }

    private List<RespoProdukDTO> mapToDTO(List<Produk> l){
        return modelMapper.map(l,new TypeToken<List<RespoProdukDTO>>(){}.getType());
    }
}
