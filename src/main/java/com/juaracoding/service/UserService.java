package com.juaracoding.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.report.RepUserDTO;
import com.juaracoding.dto.response.ResUserDTO;
import com.juaracoding.dto.validation.ValUserDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.User;
import com.juaracoding.repo.UserRepo;
import com.juaracoding.security.BcryptImpl;
import com.juaracoding.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Kode Platform / Aplikasi : AUT
 * Kode Modul : 04
 * Kode Validation / Error  : FV - FE
 */
@Service
@Transactional
public class UserService implements IService<User>, IReport<User> {

    @Autowired
    private Cloudinary cloudinary;
    public static final String BASE_URL_IMAGE = System.getProperty("user.dir")+"\\image-saved";
    private static Path rootPath;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;

    private StringBuilder sBuild = new StringBuilder();

    @Override
    public ResponseEntity<Object> save(User user, HttpServletRequest request) {//001-010
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(user == null){
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST,null,"AUT04FV001",request);
            }
            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
            user.setCreatedBy(Long.parseLong(m.get("userId").toString()));
            userRepo.save(user);
        }catch (Exception e){
            return GlobalResponse.dataGagalDisimpan("AUT04FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, User user, HttpServletRequest request) {//011-020
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id == null){
                return GlobalResponse.objectIsNull("AUT04FV011",request);
            }
            if(user == null){
                return GlobalResponse.objectIsNull("AUT04FV012",request);
            }
            Optional<User> opUser = userRepo.findById(id);
            if(!opUser.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT04FV013",request);
            }
            User userDB = opUser.get();
            userDB.setNamaLengkap(user.getNamaLengkap());
            userDB.setAlamat(user.getAlamat());
            userDB.setNoHp(user.getNoHp());
            userDB.setEmail(user.getEmail());
            userDB.setTanggalLahir(user.getTanggalLahir());
            userDB.setAkses(user.getAkses());
            userDB.setModifiedBy(Long.parseLong(m.get("userId").toString()));

        }catch (Exception e){
            return GlobalResponse.dataGagalDiubah("AUT04FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {//021-030
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT04FV021",request);
            }
            Optional<User> opUser = userRepo.findById(id);
            if(!opUser.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT04FV022",request);
            }
            userRepo.deleteById(id);

        }catch (Exception e){
            return GlobalResponse.dataGagalDihapus("AUT04FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {//031-040
        Page<User> page = null;
        List<User> list = null;
        List<RepUserDTO> listDTO = null;
        Map<String,Object> data = null;
        try {
            page = userRepo.findAll(pageable);
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("AUT04FV031",request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO,page,"id","");
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT04FE031",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {//041-050
        ResUserDTO resUserDTO = null;
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT04FV041",request);

            }
            Optional<User> opUser = userRepo.findById(id);
            if(!opUser.isPresent()){
                return GlobalResponse.dataTidakDitemukan("AUT04FV042",request);
            }
            User userDB = opUser.get();
            resUserDTO = mapToDTO(userDB);
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT04FE041",request);
        }

        return GlobalResponse.dataDitemukan(resUserDTO,request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {//051-060
        Page<User> page = null;
        List<RepUserDTO> listDTO = null;
        Map<String,Object> data = null;
        try {
            switch (columnName){
                case "nama-lengkap":page = userRepo.findByNamaLengkapContainsIgnoreCase(value,pageable);break;
                case "alamat":page = userRepo.findByAlamatContainsIgnoreCase(value,pageable);break;
                case "no-hp":page = userRepo.findByNoHpContainsIgnoreCase(value,pageable);break;
//                case "tanggal-lahir":page = userRepo.findByTanggalLahirContainsIgnoreCase(value,pageable);break;
                case "email":page = userRepo.findByEmailContainsIgnoreCase(value,pageable);break;
                case "akses":page = userRepo.cariAkses(value,pageable);break;
                case "umur":page = userRepo.cariUmur(value,pageable);break;
                case "username":page = userRepo.findByUsernameContainsIgnoreCase(value,pageable);break;
                default:page = userRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("AUT04FV051",request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO,page,columnName,value);
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT04FE051",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {//061-070
        String message = "";
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(!ExcelReader.hasWorkBookFormat(multipartFile)){
                return GlobalResponse.formatHarusExcel("AUT04FV061",request);
            }
            List lt = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("AUT04FV062",request);
            }
            userRepo.saveAll(convertListWorkBookToListEntity(lt,Long.parseLong(m.get("userId").toString())));
        }catch (Exception e){
            return GlobalResponse.terjadiKesalahan("AUT04FE061",request);
        }
        return GlobalResponse.uploadFileExcelBerhasil(request);
    }

    @Override
    public List<User> convertListWorkBookToListEntity(List<Map<String, String>> workBookData,
                                                           Long userId) {
        List<User> list = new ArrayList<>();
        for (Map<String,String> map:workBookData) {
            User user = new User();
            user.setNamaLengkap(map.get("Nama-Lengkap"));
            user.setAlamat(map.get("Alamat"));
            user.setNoHp(map.get("No-Hp"));
            user.setTanggalLahir(LocalDate.parse(map.get("Tanggal-Lahir").toString()));
            user.setEmail(map.get("Email"));
            user.setCreatedBy(userId);
            list.add(user);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String column, String value,
                                    HttpServletRequest request, HttpServletResponse response) {//071-080
        List<User> listUser = null;
        try {
            switch (column){
                case "nama-lengkap":listUser = userRepo.findByNamaLengkapContainsIgnoreCase(value);break;
                case "alamat":listUser = userRepo.findByAlamatContainsIgnoreCase(value);break;
                case "no-hp":listUser = userRepo.findByNoHpContainsIgnoreCase(value);break;
//                case "tanggal-lahir":listUser = userRepo.findByTanggalLahirContainsIgnoreCase(value);break;
                case "email":listUser = userRepo.findByEmailContainsIgnoreCase(value);break;
                case "username":listUser = userRepo.findByUsernameContainsIgnoreCase(value);break;
                case "akses":listUser = userRepo.cariAkses(value);break;
                case "umur":listUser = userRepo.cariUmur(value);break;
                default:listUser= userRepo.findAll();break;
            }
            if(listUser.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataTidakDitemukan("AUT04FV071",request));
                return;
            }
            List<RepUserDTO> listDTO = mapToDTO(listUser);

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=user_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//user_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RepUserDTO());
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
                    manualResponse(response,GlobalResponse.terjadiKesalahan("AUT04FE071",request));
            return;
        }
    }

    @Override
    public void downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {//081-090
        List<User> listUser = null;
        try {
            switch (column){
                case "nama-lengkap":listUser = userRepo.findByNamaLengkapContainsIgnoreCase(value);break;
                case "alamat":listUser = userRepo.findByAlamatContainsIgnoreCase(value);break;
                case "no-hp":listUser = userRepo.findByNoHpContainsIgnoreCase(value);break;
                case "username":listUser = userRepo.findByUsernameContainsIgnoreCase(value);break;
                case "email":listUser = userRepo.findByEmailContainsIgnoreCase(value);break;
                case "akses":listUser = userRepo.cariAkses(value);break;
                case "umur":listUser = userRepo.cariUmur(value);break;
                default:listUser= userRepo.findAll();break;
            }

            if(listUser.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataTidakDitemukan("AUT04FV081",request));
                return;
            }
            List<RepUserDTO> listDTO = mapToDTO(listUser);
            int intRepUserDTOSize = listDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepUserDTO());
            List<String> listTemp = new ArrayList<>();
            List<String> listHelper = new ArrayList<>();
            for (Map.Entry<String,Object> m:mapColumnName.entrySet()) {
                listTemp.add(GlobalFunction.camelToStandard(m.getKey()));
                listHelper.add(m.getKey());
            }

            Map<String,Object> mapTemp = null;
            List<Map<String,Object>> listMap = new ArrayList<>();
            for (int i = 0; i < intRepUserDTOSize; i++) {
                mapTemp = GlobalFunction.convertClassToMap(listDTO.get(i));
                listMap.add(mapTemp);
            }

            mapResponse.put("title","REPORT DATA MENU");
            mapResponse.put("listKolom",listTemp);
            mapResponse.put("listHelper",listHelper);
            mapResponse.put("listContent",listMap);
            mapResponse.put("totalData",intRepUserDTOSize);
            mapResponse.put("username","Paul");

            context.setVariables(mapResponse);
            strHtml = springTemplateEngine.process("global-report",context);
            pdfGenerator.htmlToPdf(strHtml,"user",response);
        }catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.terjadiKesalahan("AUT04FE081",request));
            return;
        }
    }

    /** additional function */

    public User mapToUser(ValUserDTO valUserDTO){
        return modelMapper.map(valUserDTO, User.class);
    }

    public List<RepUserDTO> mapToDTO(List<User> listUser){
//        return modelMapper.map(listUser,new TypeToken<List<RepUserDTO>>(){}.getType());
        List<RepUserDTO> ltRepUserDTO = new ArrayList<>();
        RepUserDTO repUserDTO = null;
        for(User user : listUser){
            repUserDTO = new RepUserDTO();
            repUserDTO.setId(user.getId());
            repUserDTO.setNamaAkses(user.getAkses()==null?"":user.getAkses().getNama());
            repUserDTO.setNoHp(user.getNoHp());
//            repUserDTO.setAlamat(user.getAlamat());
//            repUserDTO.setPassword(user.getPassword());
//            repUserDTO.setEmail(user.getEmail());
            repUserDTO.setUsername(user.getUsername());
            repUserDTO.setNamaLengkap(user.getNamaLengkap());
            repUserDTO.setUmur(user.getUmur());
            repUserDTO.setTanggalLahir(user.getTanggalLahir().format(DateTimeFormatter.ISO_DATE.ofPattern("dd LLLL yyyy")));
            ltRepUserDTO.add(repUserDTO);
        }
        return ltRepUserDTO;
    }

    public ResUserDTO mapToDTO(User user){
        return modelMapper.map(user,ResUserDTO.class);
    }

    public ResponseEntity<Object> save(User user,MultipartFile file, HttpServletRequest request) {//001-010
        Map<String,Object> m = GlobalFunction.extractToken(request);
        Map map ;
        try{
            if(user == null){
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST,null,"AUT04FV001",request);
            }

            rootPath = Paths.get(BASE_URL_IMAGE+"/"+new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date()));
            String strPathz = rootPath.toAbsolutePath().toString();
            String strPathzImage = strPathz+"\\"+file.getOriginalFilename();
            save(file);

            map = cloudinary.uploader().upload(strPathzImage, ObjectUtils.asMap("public_id",file.getOriginalFilename()));
            user.setPathImage(strPathzImage);
            user.setRegistered(true);
            user.setLinkImage(map.get("secure_url").toString());
            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
            user.setCreatedBy(Long.parseLong(m.get("userId").toString()));
            userRepo.save(user);
        }catch (Exception e){
            return GlobalResponse.dataGagalDisimpan("AUT04FE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    public void save(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Gagal Untuk menyimpan File kosong !!");
            }
            Path destinationFile = this.rootPath.resolve(Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootPath.toAbsolutePath())) {
                // This is a security check
                throw new IllegalArgumentException(
                        "Tidak Dapat menyimpan file diluar storage yang sudah ditetapkan !!");
            }
            Files.createDirectories(this.rootPath);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Failed to store file.", e);
        }
    }


    public ResponseEntity<Object> uploadImage(String username, MultipartFile file, HttpServletRequest request){
        Map map ;
        Map<String,Object> mapResponse ;
        Optional<User> userOptional = userRepo.findByUsername(username);
        if(!userOptional.isPresent()){
            return GlobalResponse.dataTidakDitemukan("XERRORUPLOAD",request);
        }
        rootPath = Paths.get(BASE_URL_IMAGE+"/"+new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date()));
        String strPathz = rootPath.toAbsolutePath().toString();
        String strPathzImage = strPathz+"\\"+file.getOriginalFilename();
        save(file);

        try {
            map = cloudinary.uploader().upload(strPathzImage, ObjectUtils.asMap("public_id",file.getOriginalFilename()));
            User userDB = userOptional.get();
            userDB.setModifiedBy(userDB.getId());
            userDB.setModifiedDate(LocalDateTime.now());
            userDB.setPathImage(strPathzImage);
            userDB.setLinkImage(map.get("secure_url").toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        Map<String,Object> m = new HashMap<>();
        m.put("url-img",map.get("secure_url").toString());
        return ResponseEntity.status(HttpStatus.OK).body(m);
//        return GlobalResponse.dataResponseObject(m,request);
    }

}