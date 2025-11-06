package com.juaracoding.controller;

import com.juaracoding.model.Akses;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.model.Menu;
import com.juaracoding.repo.AksesRepo;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.repo.MenuRepo;
import com.juaracoding.utils.DataGenerator;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CreateData extends AbstractTestNGSpringContextTests {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private AksesRepo aksesRepo;

    private JSONObject req;
    private GroupMenu groupMenu;
    private Menu menu;
    private Akses akses;

    private Random rand ;
    private DataGenerator dataGenerator;

    @BeforeClass
    private void init(){
        rand  = new Random();
        req = new JSONObject();
        groupMenu = new GroupMenu();
        akses = new Akses();
        menu = new Menu();
        dataGenerator = new DataGenerator();
    }

    @BeforeTest
    private void setup(){
        /** sifatnya optional */
    }

    @Test(priority = 0)
    void saveGroupMenu(){
        Response response ;
        try{
            List<GroupMenu> groupMenus = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                GroupMenu gMenuAdd = new GroupMenu();
                gMenuAdd.setCreatedBy(1L);
                gMenuAdd.setDeskripsi(genDataDescription());
                gMenuAdd.setNama(dataGenerator.dataNamaTim());
                groupMenus.add(gMenuAdd);
            }
            groupMenuRepo.saveAll(groupMenus);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 1)
    void saveMenu(){
        Response response ;
        try{
            List<Menu> menus = new ArrayList<>();
            List<GroupMenu> groupMenus = groupMenuRepo.findAll();
            int intSize = groupMenus.size();

            for (int i = 0; i < 10; i++) {
                Menu addMenu = new Menu();
                String nama = dataGenerator.dataNamaTim();
                String path = "/"+nama.toLowerCase().replace(" ","-");
                addMenu.setCreatedBy(1L);
                addMenu.setDeskripsi(genDataDescription());
                addMenu.setNama(dataGenerator.dataNamaTim());
                addMenu.setPath(path);
                addMenu.setGroupMenu(groupMenus.get(rand.nextInt(intSize)));
                menus.add(addMenu);
            }
            menuRepo.saveAll(menus);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 2)
    void saveAkses(){
        Response response ;
        try{
            List<Akses> aksesList = new ArrayList<>();
            List<Menu> menus = menuRepo.findAll();
            int intMenuSize = menus.size();


            for (int i = 0; i < 10; i++) {
                /** Harus Menggunakan Set agar tidak redundant datanya */
                Set<Menu> menusAdd = new HashSet<>();
                List<Menu> listMenu = new ArrayList<>();
                Akses addAkses = new Akses();
                addAkses.setCreatedBy(1L);
                addAkses.setDeskripsi(genDataDescription());
                addAkses.setNama(dataGenerator.dataNamaTim());
                for (int j = 0; j < rand.nextInt(intMenuSize); j++) {
                    menusAdd.add(menus.get(rand.nextInt(intMenuSize)));
                }
                Iterator it = menusAdd.iterator();
                while (it.hasNext()) {
                    listMenu.add((Menu) it.next());
                }
                addAkses.setListMenu(listMenu);
                aksesList.add(addAkses);
            }
            aksesRepo.saveAll(aksesList);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private String genDataDescription(){
        String strArr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        int intLength =  rand.nextInt(20,200);
        for (int i = 0; i < intLength; i++) {
            sb.append(strArr.charAt(rand.nextInt(strArr.length())));
        }

        return sb.toString();
    }
}