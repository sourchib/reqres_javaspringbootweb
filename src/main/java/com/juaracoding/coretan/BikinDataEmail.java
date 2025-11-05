package com.juaracoding.coretan;

import java.util.Random;

public class BikinDataEmail {
    /**
     * alfabetkecil -> 8 - 16 digit
     * angka -> 1-3 digit kecuali konsisten 3 digit (100,1000)
     * @
     * provider -> ["gmail","yahoo","rocketmail","ymail"]
     * .
     * domain -> {"com","co.id","id","net"}
     * @param args
     */
    public static void main(String[] args) {
        Random r = new Random();
        StringBuilder sBuild = new StringBuilder();
        char[] chArrAlfa = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String [] strArrProvider = {"gmail","yahoo","rocketmail","ymail"};
        String [] strDomain = {"com","co.id","id","net"};
        int lengthProvider = strArrProvider.length;
        int lengthDomain = strDomain.length;
        for (int i = 0; i < 100000; i++) {
            sBuild.setLength(0);
            int intDigitAlfa = r.nextInt(8,17);
            for (int j = 0; j < intDigitAlfa; j++) {
                sBuild.append(chArrAlfa[r.nextInt(0,chArrAlfa.length)]);
            }
            sBuild.append(r.nextInt(1000)).append("@").
                    append(strArrProvider[r.nextInt(lengthProvider)]).append(".").
                    append(strDomain[r.nextInt(lengthDomain)]);
            System.out.println(sBuild);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(r.nextLong(1000000000000000L,9999999999999999L));
        }
    }

}