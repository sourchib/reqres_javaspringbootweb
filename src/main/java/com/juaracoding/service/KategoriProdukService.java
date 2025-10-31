package com.juaracoding.service;

import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.RelationDTO;
import com.juaracoding.dto.response.RespoKategoriProdukDTO;
import com.juaracoding.dto.validation.ValKategoriProdukDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.KategoriProduk;
import com.juaracoding.model.Supplier;
import com.juaracoding.repo.KategoriProdukRepo;
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
 * modul code : 01
 */
@Service
@Transactional
public class KategoriProdukService implements IService<KategoriProduk>, IReport<KategoriProduk> {

    @Autowired
    private KategoriProdukRepo kategoriProdukRepo;

    @Autowired
    private TransformPagination tp;

    private ModelMapper modelMapper = new ModelMapper();
    private StringBuilder sBuild = new StringBuilder();
    private static final String className = "KategoriProdukService";

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;


    @Override//001-010
    public ResponseEntity<Object> save(KategoriProduk kategoriProduk, HttpServletRequest request) {

        if(kategoriProduk==null){
            return new ResponseHandler().handleResponse("Data Gagal Disimpan",
                    HttpStatus.BAD_REQUEST,null,"SLS01FV001",request);
        }
        try{
            kategoriProdukRepo.save(kategoriProduk);
        }catch (Exception e){
            LoggingFile.logException(className,"save(KategoriProduk kategoriProduk, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Disimpan",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS01FE001",request);
        }
        return new ResponseHandler().handleResponse("Data Berhasil Disimpan",
                HttpStatus.CREATED,null,null,request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, KategoriProduk kategoriProduk, HttpServletRequest request) {
        if(kategoriProduk==null){
            return new ResponseHandler().handleResponse("Data Gagal Diubah",
                    HttpStatus.BAD_REQUEST,null,"SLS01FV011",request);
        }
        try{
            Optional<KategoriProduk> optionalKategoriProduk = kategoriProdukRepo.findById(id);
            if(optionalKategoriProduk.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS01FV012",request);
            }
            KategoriProduk nextKategoriProduk = optionalKategoriProduk.get();
            nextKategoriProduk.setNama(kategoriProduk.getNama());
            nextKategoriProduk.setModifiedBy(1L);

        }catch (Exception e){
            LoggingFile.logException(className,"update(Long id,KategoriProduk kategoriProduk, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Diubah",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS01FE011",request);
        }
        return new ResponseHandler().handleResponse("Data Berhasil Diubah",
                HttpStatus.OK,null,null,request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        if(id==null){
            return new ResponseHandler().handleResponse("Data Gagal Dihapus",
                    HttpStatus.BAD_REQUEST,null,"SLS01FV021",request);
        }
        try{
            Optional<KategoriProduk> optionalKategoriProduk = kategoriProdukRepo.findById(id);
            if(optionalKategoriProduk.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS01FV022",request);
            }
            kategoriProdukRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.logException(className,"delete(Long id, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Dihapus",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS01FE021",request);
        }
        return new ResponseHandler().handleResponse("Data Berhasil Dihapus",
                HttpStatus.OK,null,null,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        KategoriProduk nextKategoriProduk =null;
        RespoKategoriProdukDTO respoKategoriProdukDTO = null;
        if(id==null){
            return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                    HttpStatus.BAD_REQUEST,null,"SLS01FV031",request);
        }
        try{
            Optional<KategoriProduk> optionalKategoriProduk = kategoriProdukRepo.findById(id);
            if(optionalKategoriProduk.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS01FV032",request);
            }
            nextKategoriProduk = optionalKategoriProduk.get();
            respoKategoriProdukDTO = entityToDTO(nextKategoriProduk);
        }catch (Exception e){
            LoggingFile.logException(className,"findById(Long id, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Data Gagal Diubah",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS01FE031",request);
        }
        return new ResponseHandler().handleResponse("Data Ditemukan",
                HttpStatus.OK,respoKategoriProdukDTO,null,request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<KategoriProduk> page=null;
        List<RespoKategoriProdukDTO> listDTO = null;
        Page<RespoKategoriProdukDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            page = kategoriProdukRepo.findAll(pageable);
            if(page.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS01FV041",request);
            }
            listDTO = entityToDTO(page.getContent());//List<KategoriProduk> -> List<RespoKategoriProduk>
            data = tp.transformPagination(listDTO,page,"id","");
        }catch (Exception e){
            LoggingFile.logException(className,"findAll(Pageable pageable, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS01FE041",request);
        }

        return new ResponseHandler().handleResponse("Data Ditemukan",
                HttpStatus.OK,data,null,request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        Page<KategoriProduk> page=null;
        List<RespoKategoriProdukDTO> listDTO = null;
        Page<RespoKategoriProdukDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column){
                case "nama":page = kategoriProdukRepo.findByNamaContainsIgnoreCase(pageable,value);break;
                default:page = kategoriProdukRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS01FV051",request);
            }
            listDTO = entityToDTO(page.getContent());//List<KategoriProduk> -> List<RespoKategoriProduk>
            data = tp.transformPagination(listDTO,page,column,value);
        }catch (Exception e){
            LoggingFile.logException(className,"findByParam(Pageable pageable, String column, String value, HttpServletRequest request)  "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS01FE051",request);
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
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE,null,"SLS01FV061",request);
            }
            List lt = new ExcelReader(file.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return new ResponseHandler().handleResponse("File Kosong",
                        HttpStatus.BAD_REQUEST,null,"SLS01FV062",request);
            }
            kategoriProdukRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        }catch (Exception e){
            LoggingFile.logException(className,"uploadDataExcel(MultipartFile file, HttpServletRequest request)   "+ RequestCapture.allRequest(request),e);
            return new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS01FE061",request);
        }
        return new ResponseHandler().handleResponse("Upload File Berhasil",
                HttpStatus.CREATED,null,null,request);
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<KategoriProduk> list = null;
        List<RespoKategoriProdukDTO> listDTO = null;
        Page<RespoKategoriProdukDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column){
                case "nama":list = kategoriProdukRepo.findByNamaContainsIgnoreCase(value);break;
                default:list = kategoriProdukRepo.findAll();
            }
            if(list.isEmpty()){
                GlobalResponse.manualResponse(response, new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND,null,"SLS01FV071",request));
                return;
            }
            listDTO = entityToDTO(list);//List<KategoriProduk> -> List<RespoKategoriProduk>

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=kategori_produk_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//akses_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RespoKategoriProdukDTO());
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
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
            LoggingFile.logException(className,"downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) "+ RequestCapture.allRequest(request),e);
            GlobalResponse.manualResponse(response, new ResponseHandler().handleResponse("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS01FE071",request));
            return;
        }
    }

    @Override
    public void downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<KategoriProduk> list = null;
        List<RespoKategoriProdukDTO> listDTO = null;
        Page<RespoKategoriProdukDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column) {
                case "nama":
                    list = kategoriProdukRepo.findByNamaContainsIgnoreCase(value);
                    break;
                default:
                    list = kategoriProdukRepo.findAll();
            }
            if (list.isEmpty()) {
                GlobalResponse.manualResponse(response, new ResponseHandler().handleResponse("Data Tidak Ditemukan",
                        HttpStatus.NOT_FOUND, null, "SLS01FV081", request));
                return;
            }
            listDTO = entityToDTO(list);//List<KategoriProduk> -> List<RespoKategoriProduk>
            int intRepAksesDTOSize = listDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RespoKategoriProdukDTO());
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
            mapResponse.put("title","REPORT DATA KATEGORI PRODUK");
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
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"SLS01FE081",request));
        }
    }

    @Override
    public List<KategoriProduk> convertListWorkBookToListEntity(List<Map<String, String>> listData, Long userId) {
        List<KategoriProduk> l = new ArrayList<>();
        String value ="";
        for (Map<String,String> m:listData){
            KategoriProduk p = new KategoriProduk();
            p.setNama(m.get("Nama-Kategori-Produk"));//nama kolom di file excel
            p.setCreatedBy(userId);
            l.add(p);
        }
        return l;
    }

    public KategoriProduk mapDtoToEntity(ValKategoriProdukDTO valKategoriProdukDTO){
        KategoriProduk kategoriProduk = new KategoriProduk();
        kategoriProduk.setNama(valKategoriProdukDTO.getNama());
        return kategoriProduk;
    }

    public RespoKategoriProdukDTO entityToDTO(KategoriProduk kategoriProduk){
        RespoKategoriProdukDTO r = new RespoKategoriProdukDTO();
        r.setId(kategoriProduk.getId());
        r.setNama(kategoriProduk.getNama());
        return r;
    }

    public List<RespoKategoriProdukDTO> entityToDTO(List<KategoriProduk> kategoriProduk){
        List<RespoKategoriProdukDTO> l = new ArrayList<>();
        for (int i = 0; i < kategoriProduk.size(); i++) {
            RespoKategoriProdukDTO r = new RespoKategoriProdukDTO();
            r.setId(kategoriProduk.get(i).getId());
            r.setNama(kategoriProduk.get(i).getNama());
            l.add(r);
        }
        return l;
    }

    private List<RespoKategoriProdukDTO> mapToDTO(List<KategoriProduk> l){
        return modelMapper.map(l,new TypeToken<List<RespoKategoriProdukDTO>>(){}.getType());
    }
}
