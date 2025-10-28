package com.juaracoding.security;
/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 28/10/2025 18:29
@Last Modified 28/10/2025 18:29
Version 1.0
*/


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class AESGeneratedKey {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Tambahkan Bouncy Castle Provider
        Security.addProvider(new BouncyCastleProvider());
        try {
            // Inisialisasi generator kunci AES dengan Bouncy Castle
            KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");

            // Atur panjang kunci (misalnya: 128, 192, atau 256 bit)
            keyGen.init(256);
            // Tampilkan kunci AES

            // Generate kunci AES
            SecretKey aesKey = keyGen.generateKey();
//            System.out.println(aesKey);
            // Tampilkan kunci AES
            System.out.println("AES Key: " + bytesToHex(aesKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getKey() {
        SecretKey aesKey = null;
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
            // Atur panjang kunci (misalnya: 128, 192, atau 256 bit)
            keyGen.init(256);
            // Generate kunci AES
            aesKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytesToHex(aesKey.getEncoded());
    }

    /**
     * Helper method untuk mengubah byte array menjadi string heksadesimal
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
