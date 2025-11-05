package com.juaracoding.service;


import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.report.RepMenuDTO;
import com.juaracoding.dto.response.ResMenuDTO;
import com.juaracoding.dto.validation.ValMenuDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.LogMenu;
import com.juaracoding.model.Menu;
import com.juaracoding.repo.LogMenuRepo;
import com.juaracoding.repo.MenuRepo;
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
 * Kode Modul : 02
 * Kode Validation / Error  : FV - FE
 */
@Service
@Transactional
public class MenuService implements IService<Menu>, IReport<Menu> {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private LogMenuRepo logMenuRepo;

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
    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request) {//001-010
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(menu == null){
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST,null,"AUT02FV001",request);
            }
            Long userId = Long.parseLong(m.get("userId").toString());
            menu.setCreatedBy(userId);
            menuRepo.save(menu);
            logMenuRepo.save(mapToDTO(menu,'i',userId));
        }catch (Exception e){
            return GlobalResponse.dataGagalDisimpan("AUT02FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }


    /**
     *
     * @param id - parameter id adalah bla bla bla
     * @param menu - parameter menu adalah bla bla bla
     * @param request
     * @return
     *
     * deskripsi nya....
     */
    @Override
    public ResponseEntity<Object> update(Long id, Menu menu, HttpServletRequest request) {//011-020
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id == null){
                return GlobalResponse.objectIsNull("AUT02FV011",request);
            }
            if(menu == null){
                return GlobalResponse.objectIsNull("AUT02FV012",request);
            }
            Optional<Menu> opMenu = menuRepo.findById(id);
            if(!opMenu.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT02FV013",request);
            }
            Long userId = Long.parseLong(m.get("userId").toString());
            Menu menuDB = opMenu.get();
            menuDB.setNama(menu.getNama());
            menuDB.setDeskripsi(menu.getDeskripsi());
            menuDB.setPath(menu.getPath());
            menuDB.setGroupMenu(menu.getGroupMenu());
            menuDB.setModifiedBy(userId);
            logMenuRepo.save(mapToDTO(menuDB,'u',userId));

        }catch (Exception e){
            return GlobalResponse.dataGagalDiubah("AUT02FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {//021-030
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT02FV021",request);
            }
            Optional<Menu> opMenu = menuRepo.findById(id);
            if(!opMenu.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT02FV022",request);
            }
            Long userId = Long.parseLong(m.get("userId").toString());
            Menu menuDB = opMenu.get();
            menuRepo.deleteById(id);
            logMenuRepo.save(mapToDTO(menuDB,'d',userId));

        }catch (Exception e){
            return GlobalResponse.dataGagalDihapus("AUT02FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {//031-040
        Page<Menu> page = null;
        List<Menu> list = null;
        List<RepMenuDTO> listDTO = null;
        Map<String,Object> data = null;
        try {
            page = menuRepo.findAll(pageable);
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("AUT02FV031",request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO,page,"id","");
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT02FE031",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {//041-050
        ResMenuDTO resMenuDTO = null;
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT02FV041",request);

            }
            Optional<Menu> opMenu = menuRepo.findById(id);
            if(!opMenu.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT02FV042",request);
            }
            Menu menuDB = opMenu.get();
            resMenuDTO = mapToDTO(menuDB);
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT02FE041",request);
        }

        return GlobalResponse.dataDitemukan(resMenuDTO,request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {//051-060
        Page<Menu> page = null;
        List<RepMenuDTO> listDTO = null;
        Map<String,Object> data = null;
        try {
            switch (columnName){
                case "nama":page = menuRepo.findByNamaContainsIgnoreCase(value,pageable);break;
                case "deskripsi":page = menuRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
                case "path":page = menuRepo.findByPathContainsIgnoreCase(value,pageable);break;
                case "group":page = menuRepo.cariGroup(value,pageable);break;
                default:page = menuRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("AUT02FV051",request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO,page,columnName,value);
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT02FE051",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {//061-070
        Map<String,Object> m = GlobalFunction.extractToken(request);
        String message = "";
        try{
            if(!ExcelReader.hasWorkBookFormat(multipartFile)){
                return GlobalResponse.formatHarusExcel("AUT02FV061",request);
            }
            List lt = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("AUT02FV062",request);
            }
            menuRepo.saveAll(convertListWorkBookToListEntity(lt,Long.parseLong(m.get("userId").toString())));
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT02FE061",request);
        }

        return GlobalResponse.uploadFileExcelBerhasil(request);
    }

    @Override
    public List<Menu> convertListWorkBookToListEntity(List<Map<String, String>> workBookData,
                                                           Long userId) {
        List<Menu> list = new ArrayList<>();
        for (Map<String,String> map:workBookData) {
            Menu menu = new Menu();
            menu.setNama(map.get("Nama-Menu"));
            menu.setDeskripsi(map.get("Deskripsi-Menu"));
            menu.setPath(map.get("Path-Menu"));
            menu.setCreatedBy(userId);
            list.add(menu);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String column, String value,
                                    HttpServletRequest request, HttpServletResponse response) {//071-080
        List<Menu> listMenu = null;
        try {
            switch (column){
                case "nama" : listMenu = menuRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listMenu = menuRepo.findByDeskripsiContainsIgnoreCase(value);break;
                case "path" : listMenu = menuRepo.findByPathContainsIgnoreCase(value);break;
                case "group":listMenu = menuRepo.cariGroup(value);break;
                default:listMenu= menuRepo.findAll();break;
            }
            if(listMenu.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataTidakDitemukan("AUT02FV071",request));
                return;
            }
            List<RepMenuDTO> listDTO = mapToDTO(listMenu);

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=menu_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//menu_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RepMenuDTO());
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
                    manualResponse(response,GlobalResponse.terjadiKesalahan("AUT02FE071",request));
            return;
        }
    }

    @Override
    public void downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {//081-090
        List<Menu> listMenu = null;
        try {
            switch (column){
                case "nama" : listMenu = menuRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listMenu = menuRepo.findByDeskripsiContainsIgnoreCase(value);break;
                case "path" : listMenu = menuRepo.findByPathContainsIgnoreCase(value);break;
                case "group":listMenu = menuRepo.cariGroup(value);break;
                default:listMenu= menuRepo.findAll();break;
            }
            if(listMenu.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataTidakDitemukan("AUT02FV081",request));
                return;
            }
            List<RepMenuDTO> listDTO = mapToDTO(listMenu);
            int intRepMenuDTOSize = listDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepMenuDTO());
            List<String> listTemp = new ArrayList<>();
            List<String> listHelper = new ArrayList<>();
            for (Map.Entry<String,Object> m:mapColumnName.entrySet()) {
                listTemp.add(GlobalFunction.camelToStandard(m.getKey()));
                listHelper.add(m.getKey());
            }

            Map<String,Object> mapTemp = null;
            List<Map<String,Object>> listMap = new ArrayList<>();
            for (int i = 0; i < intRepMenuDTOSize; i++) {
                mapTemp = GlobalFunction.convertClassToMap(listDTO.get(i));
                listMap.add(mapTemp);
            }

            mapResponse.put("title","REPORT DATA MENU");
            mapResponse.put("listKolom",listTemp);
            mapResponse.put("listHelper",listHelper);
            mapResponse.put("listContent",listMap);
            mapResponse.put("totalData",intRepMenuDTOSize);
            mapResponse.put("username","Paul");

            context.setVariables(mapResponse);
            strHtml = springTemplateEngine.process("global-report",context);
            pdfGenerator.htmlToPdf(strHtml,"menu",response);
        }catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.terjadiKesalahan("AUT02FE081",request));
            return;
        }
    }

    /** additional function */

    public Menu mapToMenu(ValMenuDTO valMenuDTO){
        return modelMapper.map(valMenuDTO, Menu.class);
    }

    public List<RepMenuDTO> mapToDTO(List<Menu> listMenu){
        return modelMapper.map(listMenu,new TypeToken<List<RepMenuDTO>>(){}.getType());
    }

    public ResMenuDTO mapToDTO(Menu menu){
        return modelMapper.map(menu,ResMenuDTO.class);
    }

    public LogMenu mapToDTO(Menu menu, Character flag,Long userId){
        LogMenu logMenu = new LogMenu();
        logMenu.setIdGroupMenu(menu.getGroupMenu().getId());
        logMenu.setIdMenu(menu.getId());
        logMenu.setNama(menu.getNama());
        logMenu.setDeskripsi(menu.getDeskripsi());
//        logMenu.setPath(menu.getPath());
        logMenu.setCreatedBy(userId);
        logMenu.setFlag(flag);
        logMenu.setCreatedDate(LocalDateTime.now());
        return logMenu;
    }
}