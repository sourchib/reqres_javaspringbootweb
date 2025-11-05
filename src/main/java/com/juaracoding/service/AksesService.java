package com.juaracoding.service;


import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.report.RepAksesDTO;
import com.juaracoding.dto.response.ResAksesDTO;
import com.juaracoding.dto.validation.ValAksesDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.Akses;
import com.juaracoding.model.LogAkses;
import com.juaracoding.repo.AksesRepo;
import com.juaracoding.repo.LogAksesRepo;
import com.juaracoding.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Kode Platform / Aplikasi : AUT
 * Kode Modul : 03
 * Kode Validation / Error  : FV - FE
 */
@Service
@Transactional
public class AksesService implements IService<Akses>, IReport<Akses> {

    @Autowired
    private AksesRepo aksesRepo;

    @Autowired
    private LogAksesRepo logAksesRepo;

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private TransformPagination tp;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;

    private StringBuilder sBuild = new StringBuilder();

    @Override
    public ResponseEntity<Object> save(Akses akses, HttpServletRequest request) {//001-010
//        Robot robot = new Robot();
//        robot.keyPress(KeyEvent.VK_E);
//        robot.keyRelease(KeyEvent.VK_E);

        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(akses == null){
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST,null,"AUT03FV001",request);
            }
            Long userId = Long.parseLong(m.get("userId").toString());
            akses.setCreatedBy(userId);
            aksesRepo.save(akses);
            logAksesRepo.save(mapToDTO(akses,'i',userId));
        }catch (Exception e){
            return GlobalResponse.dataGagalDisimpan("AUT03FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Akses akses, HttpServletRequest request) {//011-020
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id == null){
                return GlobalResponse.objectIsNull("AUT03FV011",request);
            }
            if(akses == null){
                return GlobalResponse.objectIsNull("AUT03FV012",request);
            }
            Optional<Akses> opAkses = aksesRepo.findById(id);
            if(!opAkses.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT03FV013",request);
            }
            Long userId = Long.parseLong(m.get("userId").toString());
            Akses aksesDB = opAkses.get();
            aksesDB.setNama(akses.getNama());
            aksesDB.setDeskripsi(akses.getDeskripsi());
            aksesDB.setListMenu(akses.getListMenu());
            aksesDB.setModifiedBy(userId);
            logAksesRepo.save(mapToDTO(aksesDB,'u',userId));
        }catch (Exception e){
            return GlobalResponse.dataGagalDiubah("AUT03FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {//021-030
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT03FV021",request);
            }
            Optional<Akses> opAkses = aksesRepo.findById(id);
            Long userId = Long.parseLong(m.get("userId").toString());
            if(!opAkses.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT03FV022",request);
            }
            Akses aksesDB = opAkses.get();
            aksesRepo.deleteById(id);
            logAksesRepo.save(mapToDTO(aksesDB,'d',userId));
        }catch (Exception e){
            return GlobalResponse.dataGagalDihapus("AUT03FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {//031-040
        Page<Akses> page = null;
        List<Akses> list = null;
        List<RepAksesDTO> listDTO = null;
        Map<String,Object> data = null;
        try {
            page = aksesRepo.findAll(pageable);
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("AUT03FV031",request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO,page,"id","");
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT03FE031",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {//041-050
        ResAksesDTO resAksesDTO = null;
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT03FV041",request);

            }
            Optional<Akses> opAkses = aksesRepo.findById(id);
            if(!opAkses.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT03FV042",request);
            }
            Akses aksesDB = opAkses.get();
            resAksesDTO = mapToDTO(aksesDB);
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT03FE041",request);
        }

        return GlobalResponse.dataDitemukan(resAksesDTO,request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {//051-060
        Page<Akses> page = null;
        List<RepAksesDTO> listDTO = null;
        Map<String,Object> data = null;
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try {
            switch (columnName){
                case "nama":page = aksesRepo.findByNamaContainsIgnoreCase(value,pageable);break;
                case "deskripsi":page = aksesRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
                default:page = aksesRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("AUT03FV051",request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO,page,columnName,value);
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT03FE051",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {//061-070
        String message = "";
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(!ExcelReader.hasWorkBookFormat(multipartFile)){
                return GlobalResponse.formatHarusExcel("AUT03FV061",request);
            }
            List lt = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("AUT03FV062",request);
            }
            aksesRepo.saveAll(convertListWorkBookToListEntity(lt,Long.parseLong(m.get("userId").toString())));
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT03FE061",request);
        }

        return GlobalResponse.uploadFileExcelBerhasil(request);
    }

    @Override
    public List<Akses> convertListWorkBookToListEntity(List<Map<String, String>> workBookData,
                                                           Long userId) {
        List<Akses> list = new ArrayList<>();
        for (Map<String,String> map:workBookData) {
            Akses akses = new Akses();
            akses.setNama(map.get("Nama-Akses"));
            akses.setDeskripsi(map.get("Deskripsi-Akses"));
            akses.setCreatedBy(userId);
            list.add(akses);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String column, String value,
                                    HttpServletRequest request, HttpServletResponse response) {//071-080
        List<Akses> listAkses = null;
        try {
            switch (column){
                case "nama" : listAkses = aksesRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listAkses = aksesRepo.findByDeskripsiContainsIgnoreCase(value);break;
                default:listAkses= aksesRepo.findAll();break;
            }
            if(listAkses.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataTidakDitemukan("AUT03FV071",request));
                return;
            }
            List<RepAksesDTO> listDTO = mapToDTO(listAkses);

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=akses_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//akses_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RepAksesDTO());
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
            GlobalResponse.
                    manualResponse(response,GlobalResponse.terjadiKesalahan("AUT03FE071",request));
            LoggingFile.logException("AksesService","downloadReportExcel(String column, String value,\n" +
                    "HttpServletRequest request, HttpServletResponse response)",e);
            return;
        }
    }

    @Override
    public void downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {//081-090
        List<Akses> listAkses = null;
        try {
            switch (column){
                case "nama" : listAkses = aksesRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listAkses = aksesRepo.findByDeskripsiContainsIgnoreCase(value);break;
                default:listAkses= aksesRepo.findAll();break;
            }
            if(listAkses.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataTidakDitemukan("AUT03FV081",request));
                return;
            }
            List<RepAksesDTO> listDTO = mapToDTO(listAkses);
            int intRepAksesDTOSize = listDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepAksesDTO());
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

            mapResponse.put("title","REPORT DATA MENU");
            mapResponse.put("listKolom",listTemp);
            mapResponse.put("listHelper",listHelper);
            mapResponse.put("listContent",listMap);
            mapResponse.put("totalData",intRepAksesDTOSize);
            mapResponse.put("username","Paul");

            context.setVariables(mapResponse);
            strHtml = springTemplateEngine.process("global-report",context);
            pdfGenerator.htmlToPdf(strHtml,"akses",response);
        }catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.terjadiKesalahan("AUT03FE081",request));
            return;
        }
    }

    /** additional function */

    public Akses mapToAkses(ValAksesDTO valAksesDTO){
        return modelMapper.map(valAksesDTO, Akses.class);
    }

    public List<RepAksesDTO> mapToDTO(List<Akses> listAkses){
        return modelMapper.map(listAkses,new TypeToken<List<RepAksesDTO>>(){}.getType());
    }

    public ResAksesDTO mapToDTO(Akses akses){
        return modelMapper.map(akses,ResAksesDTO.class);
    }


    private LogAkses mapToDTO(Akses akses,Character flag, Long userId){
        LogAkses logAkses = new LogAkses();
        logAkses.setNama(akses.getNama());
        logAkses.setDeskripsi(akses.getDeskripsi());
        logAkses.setIdAkses(akses.getId());
        logAkses.setCreatedDate(LocalDateTime.now());
        logAkses.setCreatedBy(userId);
        logAkses.setFlag(flag);
        return logAkses;
    }
}