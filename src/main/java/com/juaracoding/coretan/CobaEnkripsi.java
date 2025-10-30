package com.juaracoding.coretan;

import com.juaracoding.security.Crypto;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 24/10/2025 21:16
@Last Modified 24/10/2025 21:16
Version 1.0
*/
public class CobaEnkripsi {

    public static void main(String[] args) {
        String strEncrypt = Crypto.performEncrypt("paul.123");
        System.out.println(strEncrypt);
        System.out.println(Crypto.performDecrypt(strEncrypt));
    }
}
