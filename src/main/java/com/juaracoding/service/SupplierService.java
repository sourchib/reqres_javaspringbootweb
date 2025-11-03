package com.juaracoding.service;

import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.response.RespoSupplierDTO;
import com.juaracoding.dto.validation.ValSupplierDTO;
import com.juaracoding.model.LogSupplier;
import com.juaracoding.model.Supplier;
import com.juaracoding.repo.LogSupplierRepo;
import com.juaracoding.repo.SupplierRepo;
import com.juaracoding.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * modul code : 02
 */
@Service
@Transactional
public class SupplierService implements IService<Supplier>, IReport<Supplier> {

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private LogSupplierRepo logSupplierRepo;

    @Autowired
    private TransformPagination tp;

    private ModelMapper modelMapper = new ModelMapper();
    private StringBuilder sBuild = new StringBuilder();
    private static final String className = "SupplierService";

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;


    @Override//001-010
    public ResponseEntity<Object> save(Supplier supplier, HttpServletRequest request) {

        if(supplier==null){
            return GlobalResponse.dataGagalDisimpan("SLS02FV001",request);
        }
        try{
            supplierRepo.save(supplier);
            logSupplierRepo.save(mapToDTO(supplier,'i',1L));
        }catch (Exception e){
            LoggingFile.logException(className,"save(Supplier supplier, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.dataGagalDisimpan("SLS02FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Supplier supplier, HttpServletRequest request) {
        if(supplier==null){
            return GlobalResponse.dataGagalDiubah("SLS02FV011",request);
        }
        try{
            Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
            if(optionalSupplier.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLS02FV012",request);
            }
            Supplier nextSupplier = optionalSupplier.get();
            nextSupplier.setNama(supplier.getNama());
            nextSupplier.setModifiedBy(1L);
            logSupplierRepo.save(mapToDTO(nextSupplier,'u',1L));
        }catch (Exception e){
            LoggingFile.logException(className,"update(Long id,Supplier supplier, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.dataGagalDiubah("SLS02FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        if(id==null){
            return GlobalResponse.dataGagalDihapus("SLS02FV021",request);
        }
        try{
            Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
            if(optionalSupplier.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLS02FV022",request);
            }
            Supplier nextSupplier = optionalSupplier.get();
            supplierRepo.deleteById(id);
            logSupplierRepo.save(mapToDTO(nextSupplier,'d',1L));
        }catch (Exception e){
            LoggingFile.logException(className,"delete(Long id, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);
            return GlobalResponse.dataGagalDihapus("SLS02FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Supplier nextSupplier =null;
        RespoSupplierDTO respoSupplierDTO = null;
        if(id==null){
            return GlobalResponse.dataTidakDitemukan("SLS02FV031",request);
        }
        try{
            Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
            if(optionalSupplier.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLS02FV032",request);
            }
            nextSupplier = optionalSupplier.get();
            respoSupplierDTO = entityToDTO(nextSupplier);
        }catch (Exception e){
            LoggingFile.logException(className,"findById(Long id, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.terjadiKesalahan("SLS02FE031",request);
        }
        return GlobalResponse.dataDitemukan(respoSupplierDTO,request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Supplier> page=null;
        List<RespoSupplierDTO> listDTO = null;
        Page<RespoSupplierDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            page = supplierRepo.findAll(pageable);
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLS02FV041",request);
            }
            listDTO = entityToDTO(page.getContent());//List<Supplier> -> List<RespoSupplier>
            data = tp.transformPagination(listDTO,page,"id","");
        }catch (Exception e){
            LoggingFile.logException(className,"findAll(Pageable pageable, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.terjadiKesalahan("SLS02FE041",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        Page<Supplier> page=null;
        List<RespoSupplierDTO> listDTO = null;
        Page<RespoSupplierDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column){
                case "nama":page = supplierRepo.findByNamaContainsIgnoreCase(pageable,value);break;
                default:page = supplierRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLS02FV051",request);
            }
            listDTO = entityToDTO(page.getContent());//List<Supplier> -> List<RespoSupplier>
            data = tp.transformPagination(listDTO,page,column,value);
        }catch (Exception e){
            LoggingFile.logException(className,"findByParam(Pageable pageable, String column, String value, HttpServletRequest request)  "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.terjadiKesalahan("SLS02FE051",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try{
            if(!ExcelReader.hasWorkBookFormat(file)){
                return GlobalResponse.formatHarusExcel("SLS02FV061",request);
            }
            List lt = new ExcelReader(file.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("SLS02FV062",request);
            }
            List<Supplier> ls = convertListWorkBookToListEntity(lt,1L);
            supplierRepo.saveAll(ls);
            logSupplierRepo.saveAll(mapToDTO(ls,'i',1L));
        }catch (Exception e){
            LoggingFile.logException(className,"uploadDataExcel(MultipartFile file, HttpServletRequest request)   "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.terjadiKesalahan("SLS02FE061",request);
        }
        return GlobalResponse.uploadFileExcelBerhasil(request);
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Supplier> list = null;
        List<RespoSupplierDTO> listDTO = null;
        Page<RespoSupplierDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column){
                case "nama":list = supplierRepo.findByNamaContainsIgnoreCase(value);break;
                default:list = supplierRepo.findAll();
            }
            if(list.isEmpty()){

                GlobalResponse.manualResponse(response, GlobalResponse.terjadiKesalahan("SLS02FV071",request));
                return;
            }
            listDTO = entityToDTO(list);//List<Supplier> -> List<RespoSupplier>

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=supplier_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RespoSupplierDTO());
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
            GlobalResponse.manualResponse(response, GlobalResponse.terjadiKesalahan("SLS02FE071",request));
            return;
        }
    }

    @Override
    public void downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Supplier> list = null;
        List<RespoSupplierDTO> listDTO = null;
        Page<RespoSupplierDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column) {
                case "nama":
                    list = supplierRepo.findByNamaContainsIgnoreCase(value);
                    break;
                default:
                    list = supplierRepo.findAll();
            }
            if (list.isEmpty()) {
                GlobalResponse.manualResponse(response, GlobalResponse.dataTidakDitemukan("SLS02FV081",request));
                return;
            }
            listDTO = entityToDTO(list);//List<Supplier> -> List<RespoSupplier>
            int intRepAksesDTOSize = listDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RespoSupplierDTO());
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
            pdfGenerator.htmlToPdf(strHtml,"supplier",response);
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
            LoggingFile.logException(className,"downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) "+ RequestCapture.allRequest(request),e);
            GlobalResponse.manualResponse(response, GlobalResponse.terjadiKesalahan("SLS02FE081",request));
        }
    }

    @Override
    public List<Supplier> convertListWorkBookToListEntity(List<Map<String, String>> listData, Long userId) {
        List<Supplier> l = new ArrayList<>();
        String value ="";
        for (Map<String,String> m:listData){
            Supplier p = new Supplier();
            p.setNama(m.get("Nama-Supplier"));//nama kolom di file excel
            p.setCreatedBy(userId);
            l.add(p);
        }
        return l;
    }

    /** Map untuk request */
    public Supplier mapDtoToEntity(ValSupplierDTO valSupplierDTO){
        Supplier supplier = new Supplier();
        supplier.setNama(valSupplierDTO.getNama());
        return supplier;
    }
    public RespoSupplierDTO entityToDTO(Supplier supplier){
        RespoSupplierDTO r = new RespoSupplierDTO();
        r.setId(supplier.getId());
        r.setNama(supplier.getNama());
        return r;
    }

    public List<RespoSupplierDTO> entityToDTO(List<Supplier> supplier){
        List<RespoSupplierDTO> l = new ArrayList<>();
        for (int i = 0; i < supplier.size(); i++) {
            RespoSupplierDTO r = new RespoSupplierDTO();
            r.setId(supplier.get(i).getId());
            r.setNama(supplier.get(i).getNama());
            l.add(r);
        }
        return l;
    }

    private List<RespoSupplierDTO> mapToDTO(List<Supplier> l){
        return modelMapper.map(l,new TypeToken<List<RespoSupplierDTO>>(){}.getType());
    }

    private LogSupplier mapToDTO(Supplier supplier,Character flag, Long userId){
        LogSupplier l = new LogSupplier();
        l.setIdSupplier(supplier.getId());
        l.setNama(supplier.getNama());
        l.setFlag(flag);
        l.setCreatedBy(userId);

        return l;

    }
    private List<LogSupplier> mapToDTO(List<Supplier> listSupplier,Character flag, Long userId){
        List<LogSupplier> list = new ArrayList<>();
        for (Supplier s:
             listSupplier) {
            LogSupplier l = new LogSupplier();
            l.setIdSupplier(s.getId());
            l.setNama(s.getNama());
            l.setFlag(flag);
            l.setCreatedBy(userId);
            list.add(l);
        }
        return list;
    }
}
