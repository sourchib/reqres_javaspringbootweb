package com.juaracoding.controller;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.rel.RelGroupMenuDTO;
import com.juaracoding.model.Menu;
import com.juaracoding.repo.MenuRepo;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MenuControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MenuRepo menuRepo;

    private JSONObject req;
    private Menu menu;
    private Random rand ;
    private String token;
    private DataGenerator dataGenerator;

    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        rand  = new Random();
        req = new JSONObject();
        menu = new Menu();
        dataGenerator = new DataGenerator();
        Optional<Menu> op = menuRepo.findTop1ByOrderByIdDesc();
        menu = op.get();
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
            req.put("nama", nama);
            req.put("path", path);
            req.put("deskripsi", "oijasoijdoainsoiuhbgiuhasdAAbb");
            RelGroupMenuDTO group = new RelGroupMenuDTO();
            group.setId(menu.getGroupMenu().getId());
            req.put("group-menu",group);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.POST,"menu");

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
            String reqNama = dataGenerator.dataNamaTim();
            String reqDeskripsi = "oaiusdoiajdsoijaoijaojdsiadasdadsoaiusdoiajdsoijaoijaojdsiadasdads";
            String reqPath = "/"+reqNama.toLowerCase().replace(" ","-");


            RelGroupMenuDTO relGroupMenu = new RelGroupMenuDTO();
            relGroupMenu.setId(menu.getGroupMenu().getId());

            System.out.println("ReqNama : "+reqNama);
            System.out.println("ReqDeskripsi : "+reqNama);
            menu.setNama(reqNama);
            menu.setDeskripsi(reqDeskripsi);
            menu.setPath(reqPath);
            menu.setGroupMenu(menu.getGroupMenu());

            req.put("nama", reqNama);
            req.put("path", reqPath);
            req.put("deskripsi",reqDeskripsi);
            req.put("group-menu", relGroupMenu);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.PUT,"menu/"+ menu.getId());

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
                    request(Method.GET,"/menu/"+ menu.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA DITEMUKAN");
            Assert.assertEquals(Long.parseLong(jsonPath.getString("data.id")), menu.getId());
            Assert.assertEquals(jsonPath.getString("data.nama"), menu.getNama());
            Assert.assertEquals(jsonPath.getString("data.deskripsi"), menu.getDeskripsi());
            Assert.assertEquals(jsonPath.getString("data.path"), menu.getPath());
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
                    request(Method.GET,"/menu/");

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
        String pathVariable = "/menu/asc/id/0";
        String strValue = menu.getNama();

        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","nama").
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

            Assert.assertEquals(map.get("nama"),strValue);
            Assert.assertEquals(map.get("deskripsi"), menu.getDeskripsi());
            Assert.assertEquals(map.get("path"), menu.getPath());
            Assert.assertEquals(Long.parseLong(map.get("id").toString()), menu.getId());

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
                    multiPart("file",new File(System.getProperty("user.dir")+"/src/test/resources/data-test/menu.xlsx"),
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                    request(Method.POST,"menu/upload-excel");

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
                    params("column","nama").
                    params("value", menu.getNama()).
                    request(Method.GET,"menu/download-excel");

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
                    params("column","nama").
                    params("value", menu.getNama()).
                    request(Method.GET,"menu/download-pdf");

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
                    request(Method.DELETE,"/menu/"+ menu.getId());

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