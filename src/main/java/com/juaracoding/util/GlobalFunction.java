package com.juaracoding.util;

import java.util.regex.Pattern;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 29/10/2025 22:01
@Last Modified 29/10/2025 22:01
Version 1.0
*/
public class GlobalFunction {


    public static Boolean checkValue(String value,String pattern){
        Boolean isValid = Pattern.compile(pattern).matcher(value).find();
        return isValid;
    }
}
