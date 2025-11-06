package com.juaracoding.controller;

import com.juaracoding.coretan.CoratCoret;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CoratCoretTest {

    CoratCoret coratCoret;

    @BeforeClass
    public void setup(){
        coratCoret = new CoratCoret();
    }

    @Test(priority = 0)
    public void tambah(){
        Double d = coratCoret.tambah(1,2);//actual
        Assert.assertEquals(d,3.0);
    }

    @Test(priority = 10)
    public void kurang(){
        Double d = coratCoret.kurang(5,2);//actual
        Assert.assertEquals(d,3.0);
    }

    @Test(priority = 20)
    public void kali(){
        Double d = coratCoret.kali(5,2);//actual
        Assert.assertEquals(d,10.0);
    }
}