package com.juaracoding.controller;

import com.juaracoding.coretan.ReqDataExternal;
import com.juaracoding.httpclient.DataService;
import com.juaracoding.util.LoggingFile;
import com.juaracoding.util.RequestCapture;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("external")
public class ExternalController {

    @Autowired
    private DataService dataService;

    private static final String className = "ExternalController";

    @GetMapping("/{nama}")
    public String callWelcome(
            @PathVariable String nama
    ){
        String strText = dataService.welcome();
        strText = strText +" : "+ nama;
        return strText;
    }

    @PostMapping
    public Map<String,Object> callData(@RequestBody ReqDataExternal reqDataExternal){
        Map<String,Object> map = dataService.data1(
                String.valueOf(reqDataExternal.getId()),
                reqDataExternal.getNama(),
                reqDataExternal.getAlamat(),
                reqDataExternal.getTanggalLahir(),
                reqDataExternal.getEmail()
        );
//        System.out.println(reqDataExternal.getEmail());
        return map;
    }
    @PostMapping("/rgx")
    public Map<String,Object> callData1(@Valid @RequestBody ReqDataExternal reqDataExternal){
        Map<String,Object> map = new HashMap<>();
        map.put("nama",reqDataExternal.getNama());
        map.put("alamat",reqDataExternal.getAlamat());
        map.put("tanggalLahir",reqDataExternal.getTanggalLahir());
        map.put("email",reqDataExternal.getEmail());
//        System.out.println(reqDataExternal.getEmail());
        return map;
    }

    @PostMapping("/mp")
    public Map<String,Object> callData(
            @RequestParam MultipartFile file,
            @RequestParam String data
            ){
        Map<String,Object> map = dataService.cobaData(
                file,
                data
        );

        System.out.println("File : "+map.get("file"));
        System.out.println("Data : "+map.get("data"));
        List<Map<String,Object>> l = (List<Map<String, Object>>) map.get("listData");
        for (Map<String,Object> m:
             l) {
            for (Map.Entry<String,Object> mObject:
                 m.entrySet()) {
                System.out.println("Key : "+mObject.getKey()+" Value : "+mObject.getValue());
            }
            System.out.println("=========");
        }
        return map;
    }

    @GetMapping("/errorin")
    public String errorin(){
        try{
            int x = 1/0;
            Thread.sleep(1000);
        }catch(InputMismatchException e){
            LoggingFile.logException(className,"errorin()",e);
        }catch (InterruptedException e){
            LoggingFile.logException(className,"errorin()",e);
        }
        catch (ArithmeticException e){
            LoggingFile.logException(className,"errorin()",e);
        }
        catch (NullPointerException e){
            LoggingFile.logException(className,"errorin()",e);
        }
        catch (IllegalArgumentException e){
            LoggingFile.logException(className,"errorin()",e);
        }catch (Exception e){
            LoggingFile.logException(className,"errorin()",e);
        }
        return "OK";
    }

    @PostMapping("/errorin2")
    public String errorin(@RequestBody ReqDataExternal reqDataExternal, HttpServletRequest request){
        try{
            int x = 1/0;
        }catch (InputMismatchException e){
            LoggingFile.logException(className,"errorin(@RequestBody ReqDataExternal reqDataExternal)"+ RequestCapture.allRequest(request) ,e);
        }
        catch (ArithmeticException e){
            LoggingFile.logException(className,"errorin(@RequestBody ReqDataExternal reqDataExternal)"+ RequestCapture.allRequest(request) ,e);
        }
        catch (NullPointerException e){
            LoggingFile.logException(className,"errorin(@RequestBody ReqDataExternal reqDataExternal)"+ RequestCapture.allRequest(request) ,e);
        }
        catch (Exception e){
            LoggingFile.logException(className,"errorin(@RequestBody ReqDataExternal reqDataExternal)"+ RequestCapture.allRequest(request) ,e);
        }

        return "OK";
    }
}
