package com.juaracoding.controller;

import com.juaracoding.coretan.CoratCoret2;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CoratCoret2Test {


    CoratCoret2 coratCoret2;

    @BeforeClass
    public void setup(){
        coratCoret2 = new CoratCoret2();
    }

    @Test
    public void lengthString(){
        Integer intz = coratCoret2.lengthString(("Paul"));
        Assert.assertEquals(intz,4);
    }
}//suiteTest