package com.juaracoding.controller;

import com.juaracoding.utils.TokenGenerator;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HelloControllerTest extends AbstractTestNGSpringContextTests {
    private JSONObject req;
    public static final String AUTH_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYXVsLjEyMyIsImVtIjoicG9sbC5jaGlodXlAZ21haWwuY29tIiwiaHAiOiIwODEyMTMxNDEzMjEiLCJpZCI6MSwibmFsZW5nIjoiUGF1bCBNYWxhdSIsImV4cCI6MTc2MjI2NzI2NCwiaWF0IjoxNzYyMjY1NDY0fQ.MQGMeECwnu09eeP9-y0EXst9O3BemlyqSE6gnKI1xjahjdKwzwLlw6QDhoyOMpgKsqwV9dPGWQl85IJDEoPzIg";
    private String token;

    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        RestAssured.baseURI = "http://localhost:8033";
        req = new JSONObject();
    }

    @Test
    public void data() throws ParseException {
        Response response;

        response = given().
                header("Content-Type","application/json").
                header("accept","application/json").
                header("Authorization",token).
                request(Method.GET,"data");
        int intResponseCode = response.statusCode();
        JsonPath jsonPath = response.jsonPath();
        String strContentType = response.getHeader("Content-Type");
        String strNama = response.getHeader("Nama");
        String strTimestamp =  jsonPath.get("timestamp").toString();
        String formatString = strTimestamp.substring(0, strTimestamp.lastIndexOf('+'));
        System.out.println("TIMESTAMP SERVER"+strTimestamp);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
        Date date = parser.parse(formatString);
        strTimestamp = formatter.format(date);
        Assert.assertEquals(intResponseCode,200);
        Assert.assertEquals(Long.parseLong(jsonPath.get("id").toString()),1L);
        Assert.assertEquals(jsonPath.get("nama"),"Paul");
        Assert.assertEquals(strTimestamp,"2025-11-05 14");
        Assert.assertEquals(strContentType,"application/json");
    }

    @Test
    public void welcome(){
        Response response;

        response = given().
                header("Content-Type","application/json").
                header("accept","application/json").
                header("Authorization",token).
                request(Method.GET,"welcome");
        int intResponseCode = response.statusCode();
        String strBody = response.getBody().asPrettyString();
        String strContentType = response.getHeader("Content-Type");
        String strNama = response.getHeader("Nama");

        Assert.assertEquals(intResponseCode,200);
        Assert.assertEquals(strBody,"Hello World");
        Assert.assertEquals(strContentType,"application/json");
        Assert.assertEquals(strNama,"Paul");
    }
}