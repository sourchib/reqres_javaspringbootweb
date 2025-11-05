package com.juaracoding.service;


import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.report.RepGroupMenuDTO;
import com.juaracoding.dto.response.ResGroupMenuDTO;
import com.juaracoding.dto.validation.ValGroupMenuDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.model.LogGroupMenu;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.repo.LogGroupMenuRepo;
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
import java.time.LocalDate;
import java.util.*;

/**
 * Kode Platform / Aplikasi : AUT
 * Kode Modul : 01
 * Kode Validation / Error  : FV - FE
 */
@Service
@Transactional
public class GroupMenuService implements IService<GroupMenu>, IReport<GroupMenu> {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private LogGroupMenuRepo logGroupMenuRepo;

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
    public ResponseEntity<Object> save(GroupMenu groupMenu, HttpServletRequest request) {//001-010
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(groupMenu == null){
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST,null,"AUT01FV001",request);
            }
            Long userId = Long.parseLong(m.get("userId").toString());
            groupMenu.setCreatedBy(userId);
            groupMenuRepo.save(groupMenu);
            logGroupMenuRepo.save(mapToDTO(groupMenu,'i',userId));
        }catch (Exception e){
            return GlobalResponse.dataGagalDisimpan("AUT01FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, GroupMenu groupMenu, HttpServletRequest request) {//011-020
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id == null){
                return GlobalResponse.objectIsNull("AUT01FV011",request);
            }
            if(groupMenu == null){
                return GlobalResponse.objectIsNull("AUT01FV012",request);
            }
            Optional<GroupMenu> opGroupMenu = groupMenuRepo.findById(id);
            if(!opGroupMenu.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT01FV013",request);
            }
            Long userId = Long.parseLong(m.get("userId").toString());
            GroupMenu groupMenuDB = opGroupMenu.get();
            groupMenuDB.setNama(groupMenu.getNama());
            groupMenuDB.setDeskripsi(groupMenu.getDeskripsi());
            groupMenuDB.setModifiedBy(userId);
            logGroupMenuRepo.save(mapToDTO(groupMenuDB,'u',userId));
        }catch (Exception e){
            return GlobalResponse.dataGagalDiubah("AUT01FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {//021-030
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT01FV021",request);
            }
            Optional<GroupMenu> opGroupMenu = groupMenuRepo.findById(id);
            Long userId = Long.parseLong(m.get("userId").toString());
            if(!opGroupMenu.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT01FV022",request);
            }
            LogGroupMenu logGroupMenu = mapToDTO(opGroupMenu.get(),'d',userId);
            groupMenuRepo.deleteById(id);
            logGroupMenuRepo.save(logGroupMenu);
        }catch (Exception e){
            return GlobalResponse.dataGagalDihapus("AUT01FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {//031-040
        Page<GroupMenu> page = null;
        List<GroupMenu> list = null;
        List<RepGroupMenuDTO> listDTO = null;
        Map<String,Object> data = null;
        try {
            page = groupMenuRepo.findAll(pageable);
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("AUT01FV031",request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO,page,"id","");
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT01FE031",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {//041-050
        ResGroupMenuDTO resGroupMenuDTO = null;
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT01FV041",request);

            }
            Optional<GroupMenu> opGroupMenu = groupMenuRepo.findById(id);
            if(!opGroupMenu.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT01FV042",request);
            }
            GroupMenu groupMenuDB = opGroupMenu.get();
            resGroupMenuDTO = mapToDTO(groupMenuDB);
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT01FE041",request);
        }

        return GlobalResponse.dataDitemukan(resGroupMenuDTO,request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {//051-060
        Page<GroupMenu> page = null;
        List<RepGroupMenuDTO> listDTO = null;
        Map<String,Object> data = null;
        try {
            switch (columnName){
                case "nama":page = groupMenuRepo.findByNamaContainsIgnoreCase(value,pageable);break;
                case "deskripsi":page = groupMenuRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
                default:page = groupMenuRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("AUT01FV051",request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO,page,columnName,value);
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT01FE051",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    public ResponseEntity<Object> findByParam(Pageable pageable,
                                              String columnName, String value,
                                              String status,String kategori,
                                              LocalDate start, LocalDate end,
                                              HttpServletRequest request) {//051-060
        Page<GroupMenu> page = null;
        List<RepGroupMenuDTO> listDTO = null;
        Map<String,Object> data = null;
        try {
            switch (columnName){
                case "nama":page = groupMenuRepo.findByNamaContainsIgnoreCase(value,pageable);break;
                case "deskripsi":page = groupMenuRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
                default:page = groupMenuRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("AUT01FV051",request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO,page,columnName,value);
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT01FE051",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {//061-070
        String message = "";
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(!ExcelReader.hasWorkBookFormat(multipartFile)){
                return GlobalResponse.formatHarusExcel("AUT01FV061",request);
            }
            List lt = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("AUT01FV062",request);
            }
            groupMenuRepo.saveAll(convertListWorkBookToListEntity(lt,Long.parseLong(m.get("userId").toString())));
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT01FE061",request);
        }

        return GlobalResponse.uploadFileExcelBerhasil(request);
    }

    @Override
    public List<GroupMenu> convertListWorkBookToListEntity(List<Map<String, String>> workBookData,
                                                           Long userId) {
        List<GroupMenu> list = new ArrayList<>();
        for (Map<String,String> map:workBookData) {
            GroupMenu groupMenu = new GroupMenu();
            groupMenu.setNama(map.get("Nama-Group-Menu"));
            groupMenu.setDeskripsi(map.get("Deskripsi-Group-Menu"));
            groupMenu.setCreatedBy(userId);
            list.add(groupMenu);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String column, String value,
                                    HttpServletRequest request, HttpServletResponse response) {//071-080
        List<GroupMenu> listGroupMenu = null;
        try {
            switch (column){
                case "nama" : listGroupMenu = groupMenuRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listGroupMenu = groupMenuRepo.findByDeskripsiContainsIgnoreCase(value);break;
                default:listGroupMenu=groupMenuRepo.findAll();break;
            }
            if(listGroupMenu.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataTidakDitemukan("AUT01FV071",request));
                return;
            }
            List<RepGroupMenuDTO> listDTO = mapToDTO(listGroupMenu);
            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=group-menu_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//group-menu_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RepGroupMenuDTO());
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
                    manualResponse(response,GlobalResponse.terjadiKesalahan("AUT01FE071",request));
            return;
        }
    }

    @Override
    public void downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {//081-090
        List<GroupMenu> listGroupMenu = null;
        try {
            switch (column){
                case "nama" : listGroupMenu = groupMenuRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listGroupMenu = groupMenuRepo.findByDeskripsiContainsIgnoreCase(value);break;
                default:listGroupMenu=groupMenuRepo.findAll();break;
            }
            if(listGroupMenu.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataTidakDitemukan("AUT01FV081",request));
                return;
            }
            List<RepGroupMenuDTO> listDTO = mapToDTO(listGroupMenu);
            int intRepGroupMenuDTOSize = listDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepGroupMenuDTO());
            List<String> listTemp = new ArrayList<>();
            List<String> listHelper = new ArrayList<>();
            for (Map.Entry<String,Object> m:mapColumnName.entrySet()) {
                listTemp.add(GlobalFunction.camelToStandard(m.getKey()));
                listHelper.add(m.getKey());
            }

            Map<String,Object> mapTemp = null;
            List<Map<String,Object>> listMap = new ArrayList<>();
            for (int i = 0; i < intRepGroupMenuDTOSize; i++) {
                mapTemp = GlobalFunction.convertClassToMap(listDTO.get(i));
                listMap.add(mapTemp);
            }

            mapResponse.put("title","REPORT DATA GROUP MENU");
            mapResponse.put("listKolom",listTemp);
            mapResponse.put("listHelper",listHelper);
            mapResponse.put("listContent",listMap);
            mapResponse.put("totalData",intRepGroupMenuDTOSize);
            mapResponse.put("username","Paul");

            context.setVariables(mapResponse);
            strHtml = springTemplateEngine.process("global-report",context);
            pdfGenerator.htmlToPdf(strHtml,"group-menu",response);
        }catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.terjadiKesalahan("AUT01FE081",request));
            return;
        }
    }

    /** additional function */

    public GroupMenu mapToGroupMenu(ValGroupMenuDTO valGroupMenuDTO){
        return modelMapper.map(valGroupMenuDTO, GroupMenu.class);
    }

    public List<RepGroupMenuDTO> mapToDTO(List<GroupMenu> listGroupMenu){
        return modelMapper.map(listGroupMenu,new TypeToken<List<RepGroupMenuDTO>>(){}.getType());
    }

    public ResGroupMenuDTO mapToDTO(GroupMenu groupMenu){
        return modelMapper.map(groupMenu,ResGroupMenuDTO.class);
    }

    public LogGroupMenu mapToDTO(GroupMenu groupMenu,Character flag,Long userId){
        LogGroupMenu logGroupMenu = new LogGroupMenu();
        logGroupMenu.setNama(groupMenu.getNama());
        logGroupMenu.setDeskripsi(groupMenu.getDeskripsi());
        logGroupMenu.setFlag(flag);
        logGroupMenu.setIdGroupMenu(groupMenu.getId());
        logGroupMenu.setCreatedBy(userId);
        logGroupMenu.setCreatedDate(groupMenu.getCreatedDate());
        return logGroupMenu;
    }
}