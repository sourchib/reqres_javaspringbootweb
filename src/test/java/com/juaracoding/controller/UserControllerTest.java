package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.rel.RelAksesDTO;
import com.juaracoding.model.Akses;
import com.juaracoding.model.User;
import com.juaracoding.repo.AksesRepo;
import com.juaracoding.repo.UserRepo;
import com.juaracoding.utils.DataGenerator;
import com.juaracoding.utils.TokenGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AksesRepo aksesRepo;

    private Akses akses;
    private JSONObject req;
    private User user;
    private Random rand ;
    private String token;
    private DataGenerator dataGenerator;


    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        rand  = new Random();
        req = new JSONObject();
        user = new User();
        dataGenerator = new DataGenerator();
        Optional<User> op = userRepo.findTop1ByOrderByIdDesc();
        Optional<Akses> opAkses = aksesRepo.findTop1ByOrderByIdDesc();

        user = op.get();
        akses = opAkses.get();
    }

    @BeforeTest
    private void setup(){
        /** sifatnya optional */
    }

    @Test(priority = 0)
    void save(){
        Response response ;
        String nama = dataGenerator.dataNamaTim();
        String path = "/"+nama.toLowerCase().replace(" ","-");
        try{
            req.put("username", dataGenerator.dataUsername());
            req.put("password", dataGenerator.dataPassword());
            req.put("email", dataGenerator.dataEmail());
            req.put("no-hp", dataGenerator.dataNoHp());
            req.put("nama-lengkap", dataGenerator.dataNamaLengkap());
            req.put("alamat", dataGenerator.dataAlamat());
            req.put("tanggal-lahir", dataGenerator.dataTanggalLahir());
            RelAksesDTO relAksesDTO = new RelAksesDTO();
            relAksesDTO.setId(user.getAkses().getId());
            req.put("akses",relAksesDTO);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.POST,"user");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"SAVE SUCCESS !!");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 10)
    void update(){
        Response response ;
        req.clear();
        try{
            String reqUsername = dataGenerator.dataUsername();
            String reqPassword = dataGenerator.dataPassword();
            String reqEmail = dataGenerator.dataEmail();
            String reqNoHp = dataGenerator.dataNoHp();
            String reqAlamat = dataGenerator.dataAlamat();
            String reqNamaLengkap = dataGenerator.dataNamaLengkap();
            LocalDate reqTglLahir = LocalDate.parse(dataGenerator.dataTanggalLahir());

            user.setUsername(reqUsername);
            user.setPassword(reqPassword);
            user.setEmail(reqEmail);
            user.setNoHp(reqNoHp);
            user.setAlamat(reqAlamat);
            user.setNamaLengkap(reqNamaLengkap);
            user.setTanggalLahir(reqTglLahir);
//            user.setAkses(user.getAkses()); // tidak perlu di set karena diambil dari sumber yang sama

            req.put("username", reqUsername);
            req.put("password", reqPassword);
            req.put("email", reqEmail);
            req.put("no-hp", reqNoHp);
            req.put("nama-lengkap", reqNamaLengkap);
            req.put("alamat", reqAlamat);
            req.put("tanggal-lahir", reqTglLahir);
            RelAksesDTO relAksesDTO = new RelAksesDTO();
            relAksesDTO.setId(user.getAkses().getId());
            req.put("akses",relAksesDTO);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.PUT,"user/"+ user.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA BERHASIL DIUBAH");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 20)
    void findById(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.GET,"/user/"+ user.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA DITEMUKAN");
            Assert.assertEquals(Long.parseLong(jsonPath.getString("data.id")), user.getId());
            Assert.assertEquals(jsonPath.getString("data.nama-lengkap"), user.getNamaLengkap());
            Assert.assertEquals(jsonPath.getString("data.alamat"), user.getAlamat());
            Assert.assertEquals(jsonPath.getString("data.no-hp"), user.getNoHp());
            Assert.assertEquals(jsonPath.getString("data.username"), user.getUsername());
            Assert.assertEquals(jsonPath.getString("data.email"), user.getEmail());
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 30)
    void findAll(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.GET,"/user/");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            List ltData = jsonPath.getList("data.content");
            int intData = ltData.size();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA DITEMUKAN");
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
// ======================================================================================================================================================
            Assert.assertEquals(jsonPath.getString("data.sort-by"),"id");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.current-page")),0);
            Assert.assertEquals(jsonPath.getString("data.column-name"),"id");
            Assert.assertNotNull(jsonPath.getString("data.total-pages"));
            Assert.assertEquals(jsonPath.getString("data.sort"),"asc");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.size-per-page")), OtherConfig.getDefaultPaginationSize());
            Assert.assertEquals(jsonPath.getString("data.value"),"");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.total-data")),intData);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 40)
    void findByParam(){
        Response response ;
        String pathVariable = "/user/asc/id/0";
        String strValue = user.getNamaLengkap();

        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","nama-lengkap").
                    params("value",strValue).
                    params("size",10).
                    request(Method.GET,pathVariable);

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            List<Map<String,Object>> ltData = jsonPath.getList("data.content");
            int intData = ltData.size();
            Map<String,Object> map = ltData.get(0);

            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA DITEMUKAN");
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
// ======================================================================================================================================================
            Assert.assertEquals(jsonPath.getString("data.sort-by"),"id");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.current-page")),0);
            Assert.assertEquals(jsonPath.getString("data.column-name"),"nama");
            Assert.assertNotNull(jsonPath.getString("data.total-pages"));
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.total-data")),intData);
            Assert.assertEquals(jsonPath.getString("data.sort"),"asc");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.size-per-page")), 10);
            Assert.assertEquals(jsonPath.getString("data.value"),strValue);
// ======================================================================================================================================================

            Assert.assertEquals(Long.parseLong(jsonPath.getString("data.id")), user.getId());
            Assert.assertEquals(jsonPath.getString("data.nama-lengkap"), user.getNamaLengkap());
            Assert.assertEquals(jsonPath.getString("data.alamat"), user.getAlamat());
            Assert.assertEquals(jsonPath.getString("data.no-hp"), user.getNoHp());
            Assert.assertEquals(jsonPath.getString("data.tanggal-lahir"), user.getTanggalLahir().toString());
            Assert.assertEquals(jsonPath.getString("data.username"), strValue);//khusus ini karena pencarian berdasarkan username
            Assert.assertEquals(jsonPath.getString("data.email"), user.getEmail());
            Assert.assertEquals(Long.parseLong(map.get("id").toString()), user.getId());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 50)
    void uploadExcel(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","multipart/form-data").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    multiPart("file",new File(System.getProperty("user.dir")+"/src/test/resources/data-test/user.xlsx"),
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                    request(Method.POST,"user/upload-excel");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"UPLOAD FILE EXCEL BERHASIL");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test(priority = 50)
    void downloadExcel(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","username").
                    params("value", user.getUsername()).
                    request(Method.GET,"user/download-excel");

            int intResponse = response.getStatusCode();
            Assert.assertEquals(intResponse,200);
            /** khusus untuk download file harus di cek header nya */
            Assert.assertTrue(response.getHeader("Content-Disposition").contains(".xlsx"));// file nya memiliki extension .xlsx
            Assert.assertEquals(response.getHeader("Content-Type"),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");// content type wajib ini untuk excel
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 60)
    void downloadPdf(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","application/pdf").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","username").
                    params("value", user.getUsername()).
                    request(Method.GET,"user/download-pdf");

            int intResponse = response.getStatusCode();
            Assert.assertEquals(intResponse,200);
            /** khusus untuk download file harus di cek header nya */
            Assert.assertTrue(response.getHeader("Content-Disposition").contains(".pdf"));// file nya memiliki extension .xlsx
            Assert.assertEquals(response.getHeader("Content-Type"),"application/pdf");// content type wajib ini untuk excel
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 999)
    void delete(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.DELETE,"/user/"+ user.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA BERHASIL DIHAPUS");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}