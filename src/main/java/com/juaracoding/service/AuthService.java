package com.juaracoding.service;


import com.juaracoding.config.JwtConfig;
import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.MenuLoginDTO;
import com.juaracoding.dto.validation.LoginDTO;
import com.juaracoding.dto.validation.RegisDTO;
import com.juaracoding.dto.validation.VerifyRegisDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.Akses;
import com.juaracoding.model.Menu;
import com.juaracoding.model.User;
import com.juaracoding.repo.UserRepo;
import com.juaracoding.security.BcryptImpl;
import com.juaracoding.security.Crypto;
import com.juaracoding.security.JwtUtility;
import com.juaracoding.util.LoggingFile;
import com.juaracoding.util.RequestCapture;
import com.juaracoding.util.SendMailOTP;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Kode Platform / Aplikasi : 001 atau AUT
 * Kode Modul : 00
 * Kode Validation / Error  : FV - FE
 */
@Service
@Transactional
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private JwtUtility jwtUtility;

    private Random random = new Random();

    /** 001-010 */
    public ResponseEntity<Object> regis(User user, HttpServletRequest request) {
        if(user==null){
            return new ResponseHandler().handleResponse("Email Tidak Ditemukan !!",HttpStatus.BAD_REQUEST,null,"AUT00FV001",request);
        }
        if(user.getEmail()==null){
            return new ResponseHandler().handleResponse("Email Tidak Ditemukan !!",HttpStatus.BAD_REQUEST,null,"AUT00FV002",request);
        }
        Map<String,Object> m = new HashMap<>();
        try{
            int otp = random.nextInt(100000,999999);
            user.setOtp(BcryptImpl.hash(String.valueOf(otp)));
            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
            /** Default Akses untuk New member */
            Akses akses = new Akses();
            akses.setId(2L);
            user.setAkses(akses);

            userRepo.save(user);
            if(OtherConfig.getEnableAutomationTesting().equals("y")){
                m.put("otp",otp);// ini untuk automation
            }
            SendMailOTP.verifyRegisOTP("OTP UNTUK REGISTRASI",
                    user.getNamaLengkap(),user.getEmail(),String.valueOf(otp),"ver_regis.html");
            m.put("email",user.getEmail());
            Thread.sleep(1000);
        }catch (Exception e){
//            String strArr [] = {"poll.chihuy@gmail.com","alfin@gmail.com",""};
            LoggingFile.logException("AuthService","regis(User user, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);
//            new SMTPCore().sendMailWithAttachment(strArr,
//                    "Error ",
//                    "AuthService, "+"regis(User user, HttpServletRequest request)"+ RequestCapture.allRequest(request)+"    ----> "+e.getMessage(),
//                    "TLS",null);
            return new ResponseHandler().handleResponse("Server Tidak Dapat Memproses !!",HttpStatus.INTERNAL_SERVER_ERROR,null,"AUT00FE001",request);
        }
        return new ResponseHandler().handleResponse("OTP Terkirim, Cek Email !!",HttpStatus.OK,m,null,request);
    }

    /** 011-020 */
    public ResponseEntity<Object> verifyRegis(User user, HttpServletRequest request) {
        try {
            int otp = random.nextInt(100000,999999);
            Optional<User> opUser = userRepo.findByEmail(user.getEmail());
            if(!opUser.isPresent()) {
                return new ResponseHandler().handleResponse("Email Tidak Ditemukan !!",HttpStatus.BAD_REQUEST,null,"AUT00FV011",request);
            }
            User userNext = opUser.get();//ini dari database
            if(!BcryptImpl.verifyHash(user.getOtp(),userNext.getOtp())) {
                return new ResponseHandler().handleResponse("OTP Salah !!",HttpStatus.BAD_REQUEST,null,"AUT00FV012",request);
            }
            userNext.setRegistered(true);
            userNext.setModifiedBy(userNext.getId());
            userNext.setOtp(BcryptImpl.hash(String.valueOf(otp)));
        }catch (Exception e){
            LoggingFile.logException("AuthService","verifyRegis(User user, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);

            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",HttpStatus.INTERNAL_SERVER_ERROR,null,
                    "AUT00FE011",request);
        }
        return new ResponseHandler().handleResponse("Registrasi Berhasil !!",HttpStatus.OK,null,null,request);
    }

    /** 021-030 */
    public ResponseEntity<Object> login(User user, HttpServletRequest request) {
        Map<String,Object> m = new HashMap<>();
        User userNext = null;
        try{
            String username = user.getUsername();
            Optional<User> opUser = userRepo.findByUsernameOrEmailOrNoHpAndIsRegistered(username,username,username,true);
            if(!opUser.isPresent()) {
                return new ResponseHandler().handleResponse("User Tidak Ditemukan",HttpStatus.BAD_REQUEST,null,"AUT00FV021",request);
            }
            userNext = opUser.get();//diambil dari DB

            String pwdDB = userNext.getUsername()+user.getPassword();
            if(!BcryptImpl.verifyHash(pwdDB,userNext.getPassword())) {
                return new ResponseHandler().handleResponse("Username atau Password Salah !!",HttpStatus.BAD_REQUEST,null,"AUT00FV022",request);
            }
        }catch (Exception e){
            LoggingFile.logException("AuthService","login(User user, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);

            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",HttpStatus.INTERNAL_SERVER_ERROR,null,
                    "AUT00FE021",request);
        }

        Map<String,Object> mapData = new HashMap<>();
        mapData.put("em",userNext.getEmail());
        mapData.put("id",userNext.getId());
        mapData.put("hp",userNext.getNoHp());
        mapData.put("naleng",userNext.getNamaLengkap());
//        mapData.put("depId",userNext.getDepartment().getId());
//        List<MenuLoginDTO> menu = mapToMenuLoginDTO(userNext.getAkses().getListMenu());
        String token = jwtUtility.doGenerateToken(mapData,userNext.getUsername());

//        m.put("menu",new TransformationDataMenu().doTransformAksesMenuLogin(menu));
        if(JwtConfig.getTokenEncryptEnable().equals("y")){
            token = Crypto.performEncrypt(token);
        }
        m.put("token", token);
        m.put("urlImage", userNext.getLinkImage());

        return new ResponseHandler().handleResponse("Login Berhasil !!",HttpStatus.OK,m,null,request);
    }

    public ResponseEntity<Object> refreshToken(User user, HttpServletRequest request) {
        Map<String,Object> m = new HashMap<>();
        User userNext = null;
        try{
            String username = user.getUsername();
            Optional<User> opUser = userRepo.findByUsernameOrEmailOrNoHpAndIsRegistered(username,username,username,true);
            if(!opUser.isPresent()) {
                return new ResponseHandler().handleResponse("User Tidak Ditemukan",HttpStatus.BAD_REQUEST,null,"AUT00FV021",request);
            }
            userNext = opUser.get();//diambil dari DB

            String pwdDB = userNext.getUsername()+user.getPassword();
            if(!BcryptImpl.verifyHash(pwdDB,userNext.getPassword())) {
                return new ResponseHandler().handleResponse("Username atau Password Salah !!",HttpStatus.BAD_REQUEST,null,"AUT00FV022",request);
            }
        }catch (Exception e){
            LoggingFile.logException("AuthService","login(User user, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);

            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server",HttpStatus.INTERNAL_SERVER_ERROR,null,
                    "AUT00FE021",request);
        }

        Map<String,Object> mapData = new HashMap<>();
        mapData.put("em",userNext.getEmail());
        mapData.put("id",userNext.getId());
        mapData.put("hp",userNext.getNoHp());
        mapData.put("naleng",userNext.getNamaLengkap());
        String token = jwtUtility.doGenerateToken(mapData,userNext.getUsername());
        if(JwtConfig.getTokenEncryptEnable().equals("y")){
            token = Crypto.performEncrypt(token);
        }
        m.put("token", token);

        return new ResponseHandler().handleResponse("Login Berhasil !!",HttpStatus.OK,m,null,request);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opUser = userRepo.findByUsernameAndIsRegistered(username,true);
        if(!opUser.isPresent()) {
            throw new UsernameNotFoundException("Username atau Password Salah !!!");
        }
        User user = opUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getAuthorities());
    }


    public User mapToUser(VerifyRegisDTO verifyRegisDTO) {
            return  modelMapper.map(verifyRegisDTO, User.class);
    }

    public User mapToUser(RegisDTO regisDTO) {
        return  modelMapper.map(regisDTO, User.class);
    }

    public User mapToUser(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

    public List<MenuLoginDTO> mapToMenuLoginDTO(List<Menu> ltMenu){
        List<MenuLoginDTO> ltMenuDTO = new ArrayList<>();
        for (Menu menu : ltMenu) {
            MenuLoginDTO menuDTO = new MenuLoginDTO();
            menuDTO.setNama(menu.getNama());
            menuDTO.setPath(menu.getPath());
            menuDTO.setNamaGroupMenu(menu.getGroupMenu().getNama());
            ltMenuDTO.add(menuDTO);
        }

        return ltMenuDTO;
    }
}
