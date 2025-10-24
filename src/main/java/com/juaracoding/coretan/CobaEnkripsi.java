package com.juaracoding.coretan;
/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 28/10/2025 18:23
@Last Modified 28/10/2025 18:23
Version 1.0
*/
import com.juaracoding.security.Crypto;


public class CobaEnkripsi {

    public static void main(String[] args) {
        String strEncrypt = Crypto.performEncrypt("paul.123");
        System.out.println(strEncrypt);
        System.out.println(Crypto.performDecrypt(strEncrypt));
    }
}



