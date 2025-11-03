package com.juaracoding.service;

import com.juaracoding.core.IReport;
import com.juaracoding.core.IService;
import com.juaracoding.dto.RelationDTO;
import com.juaracoding.dto.response.RespoCustomerDTO;
import com.juaracoding.dto.validation.ValCustomerDTO;
import com.juaracoding.model.Customer;
import com.juaracoding.model.Supplier;
import com.juaracoding.repo.CustomerRepo;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * platform code : SLS
 * modul code : XX
 */
@Service
@Transactional
public class CustomerService implements IService<Customer>, IReport<Customer> {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransformPagination tp;

    private ModelMapper modelMapper = new ModelMapper();
    private StringBuilder sBuild = new StringBuilder();
    private static final String className = "CustomerService";

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;


    @Override//001-010
    public ResponseEntity<Object> save(Customer customer, HttpServletRequest request) {

        if(customer==null){
            return GlobalResponse.dataGagalDisimpan("SLSXXFV001",request);
        }
        try{
            customerRepo.save(customer);
        }catch (Exception e){
            LoggingFile.logException(className,"save(Customer customer, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.dataGagalDisimpan("SLSXXFE001",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Customer customer, HttpServletRequest request) {
        if(customer==null){
            return GlobalResponse.dataGagalDiubah("SLSXXFV011",request);
        }
        try{
            Optional<Customer> optionalCustomer = customerRepo.findById(id);
            if(optionalCustomer.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLSXXFV012",request);
            }
            Customer nextCustomer = optionalCustomer.get();
            nextCustomer.setAlamat(customer.getAlamat());
            nextCustomer.setEmail(customer.getEmail());
            nextCustomer.setNama(customer.getNama());
            nextCustomer.setKodePos(customer.getKodePos());
            nextCustomer.setSaldo(customer.getSaldo());
            nextCustomer.setTanggalLahir(customer.getTanggalLahir());
            nextCustomer.setModifiedBy(1L);

        }catch (Exception e){
            LoggingFile.logException(className,"update(Long id,Customer customer, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.dataGagalDiubah("SLSXXFE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        if(id==null){
            return GlobalResponse.dataGagalDihapus("SLSXXFV021",request);
        }
        try{
            Optional<Customer> optionalCustomer = customerRepo.findById(id);
            if(optionalCustomer.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLSXXFV022",request);
            }
            customerRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.logException(className,"delete(Long id, HttpServletRequest request)"+ RequestCapture.allRequest(request),e);
            return GlobalResponse.dataGagalDihapus("SLSXXFE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Customer nextCustomer =null;
        RespoCustomerDTO respoCustomerDTO = null;
        if(id==null){
            return GlobalResponse.dataTidakDitemukan("SLSXXFV031",request);
        }
        try{
            Optional<Customer> optionalCustomer = customerRepo.findById(id);
            if(optionalCustomer.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLSXXFV032",request);
            }
            nextCustomer = optionalCustomer.get();
            respoCustomerDTO = entityToDTO(nextCustomer);
        }catch (Exception e){
            LoggingFile.logException(className,"findById(Long id, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.terjadiKesalahan("SLSXXFE031",request);
        }
        return GlobalResponse.dataDitemukan(respoCustomerDTO,request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Customer> page=null;
        List<RespoCustomerDTO> listDTO = null;
        Page<RespoCustomerDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            page = customerRepo.findAll(pageable);
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLSXXFV041",request);
            }
            listDTO = entityToDTO(page.getContent());//List<Customer> -> List<RespoCustomer>
            data = tp.transformPagination(listDTO,page,"id","");
        }catch (Exception e){
            LoggingFile.logException(className,"findAll(Pageable pageable, HttpServletRequest request) "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.terjadiKesalahan("SLSXXFE041",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }


    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        Page<Customer> page=null;
        List<RespoCustomerDTO> listDTO = null;
        Page<RespoCustomerDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column){
                case "nama":page = customerRepo.findByNamaContainsIgnoreCase(pageable,value);break;
                case "email":page = customerRepo.findByEmailContainsIgnoreCase(pageable,value);break;
                case "alamat":page = customerRepo.findByAlamatContainsIgnoreCase(pageable,value);break;
                case "tanggal_lahir":page = customerRepo.findByAlamatContainsIgnoreCase(pageable,value);break;
                case "umur":page = customerRepo.cariUmur(pageable,value);break;
                case "saldo":page = customerRepo.cariSaldo(pageable,value);break;
                case "kodepos":page = customerRepo.findByKodePosContainsIgnoreCase(pageable,value);break;
                default:page = customerRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("SLSXXFV051",request);
            }
            listDTO = entityToDTO(page.getContent());//List<Customer> -> List<RespoCustomer>
            data = tp.transformPagination(listDTO,page,column,value);
        }catch (Exception e){
            LoggingFile.logException(className,"findByParam(Pageable pageable, String column, String value, HttpServletRequest request)  "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.terjadiKesalahan("SLSXXFE051",request);
        }
        return GlobalResponse.dataDitemukan(data,request);
    }

    @Override
    public ResponseEntity<Object> uploadDataExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try{
            if(!ExcelReader.hasWorkBookFormat(file)){
                return GlobalResponse.formatHarusExcel("SLSXXFV061",request);
            }
            List lt = new ExcelReader(file.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("SLSXXFV062",request);
            }
            customerRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        }catch (Exception e){
            LoggingFile.logException(className,"uploadDataExcel(MultipartFile file, HttpServletRequest request)   "+ RequestCapture.allRequest(request),e);
            return GlobalResponse.terjadiKesalahan("SLSXXFE061",request);
        }
        return GlobalResponse.uploadFileExcelBerhasil(request);
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Customer> list = null;
        List<RespoCustomerDTO> listDTO = null;
        Page<RespoCustomerDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column){
                case "nama":list = customerRepo.findByNamaContainsIgnoreCase(value);break;
                case "email":list = customerRepo.findByEmailContainsIgnoreCase(value);break;
                case "alamat":list = customerRepo.findByAlamatContainsIgnoreCase(value);break;
                case "tanggal_lahir":list = customerRepo.findByAlamatContainsIgnoreCase(value);break;
                case "umur":list = customerRepo.cariUmur(value);break;
                case "saldo":list = customerRepo.cariSaldo(value);break;
                case "kodepos":list = customerRepo.findByKodePosContainsIgnoreCase(value);break;
                default:list = customerRepo.findAll();
            }
            if(list.isEmpty()){

                GlobalResponse.manualResponse(response, GlobalResponse.terjadiKesalahan("SLSXXFV071",request));
                return;
            }
            listDTO = entityToDTO(list);//List<Customer> -> List<RespoCustomer>

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=customer_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//akses_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RespoCustomerDTO());
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
            GlobalResponse.manualResponse(response, GlobalResponse.terjadiKesalahan("SLSXXFE071",request));
            return;
        }
    }

    @Override
    public void downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Customer> list = null;
        List<RespoCustomerDTO> listDTO = null;
        Page<RespoCustomerDTO> pageRespo=null;
        Map<String,Object> data = null;
        try {
            switch (column) {
                case "nama":list = customerRepo.findByNamaContainsIgnoreCase(value);break;
                case "email":list = customerRepo.findByEmailContainsIgnoreCase(value);break;
                case "alamat":list = customerRepo.findByAlamatContainsIgnoreCase(value);break;
//                case "tanggal_lahir":list = customerRepo.findByAlamatContainsIgnoreCase(value);break;
                case "umur":list = customerRepo.cariUmur(value);break;
                case "saldo":list = customerRepo.cariSaldo(value);break;
                case "kodepos":list = customerRepo.findByKodePosContainsIgnoreCase(value);break;
                default:list = customerRepo.findAll();
            }
            if (list.isEmpty()) {
                GlobalResponse.manualResponse(response, GlobalResponse.dataTidakDitemukan("SLSXXFV081",request));
                return;
            }
            listDTO = entityToDTO(list);//List<Customer> -> List<RespoCustomer>
            int intRepAksesDTOSize = listDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RespoCustomerDTO());
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
            mapResponse.put("title","REPORT DATA CUSTOMER");
            mapResponse.put("listKolom",listTemp);
            mapResponse.put("listHelper",listHelper);
            mapResponse.put("listContent",listMap);
            mapResponse.put("totalData",intRepAksesDTOSize);
            mapResponse.put("username","Paul");
            mapResponse.put("timestamp", LocalDateTime.now());
            context.setVariables(mapResponse);
            strHtml = springTemplateEngine.process("global-report",context);
            pdfGenerator.htmlToPdf(strHtml,"customer",response);
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
            LoggingFile.logException(className,"downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) "+ RequestCapture.allRequest(request),e);
            GlobalResponse.manualResponse(response, GlobalResponse.terjadiKesalahan("SLSXXFE081",request));
        }
    }

    @Override
    public List<Customer> convertListWorkBookToListEntity(List<Map<String, String>> listData, Long userId) {
        List<Customer> l = new ArrayList<>();
        String value ="";
        for (Map<String,String> m:listData){
            Customer p = new Customer();
            p.setNama(m.get("Nama-Customer"));//nama kolom di file excel
            p.setAlamat(m.get("Alamat-Customer"));//nama kolom di file excel
            p.setKodePos(m.get("Kodepos-Customer"));//nama kolom di file excel
            p.setEmail(m.get("Email-Customer"));//nama kolom di file excel
            p.setTanggalLahir(LocalDate.parse(m.get("Tanggal-Lahir")));//nama kolom di file excel
            p.setSaldo(BigDecimal.valueOf(Integer.parseInt(m.get("Saldo").toString())));//nama kolom di file excel
            p.setCreatedBy(userId);
            l.add(p);
        }
        return l;
    }

    public Customer mapDtoToEntity(ValCustomerDTO valCustomerDTO){
        Customer nextCustomer = new Customer();
        nextCustomer.setAlamat(valCustomerDTO.getAlamat());
        nextCustomer.setEmail(valCustomerDTO.getEmail());
        nextCustomer.setNama(valCustomerDTO.getNama());
        nextCustomer.setKodePos(valCustomerDTO.getKodePos());
        nextCustomer.setSaldo(valCustomerDTO.getSaldo());
        nextCustomer.setTanggalLahir(valCustomerDTO.getTanggalLahir());
        return nextCustomer;
    }
    public RespoCustomerDTO entityToDTO(Customer customer){
        RespoCustomerDTO r = new RespoCustomerDTO();
        r.setAlamat(customer.getAlamat());
        r.setEmail(customer.getEmail());
        r.setNama(customer.getNama());
        r.setKodePos(customer.getKodePos());
        r.setSaldo(customer.getSaldo());
        r.setTanggalLahir(customer.getTanggalLahir());
        r.setUmur(customer.getUmur());
        return r;
    }

    public List<RespoCustomerDTO> entityToDTO(List<Customer> customer){
        List<RespoCustomerDTO> l = new ArrayList<>();
        for (Customer c:
             customer) {
            RespoCustomerDTO r = new RespoCustomerDTO();
            r.setId(c.getId());
            r.setAlamat(c.getAlamat());
            r.setEmail(c.getEmail());
            r.setNama(c.getNama());
            r.setKodePos(c.getKodePos());
            r.setSaldo(c.getSaldo());
            r.setTanggalLahir(c.getTanggalLahir());
            r.setUmur(c.getUmur());
            l.add(r);
        }
        return l;
    }

    private List<RespoCustomerDTO> mapToDTO(List<Customer> l){
        return modelMapper.map(l,new TypeToken<List<RespoCustomerDTO>>(){}.getType());
    }
}
