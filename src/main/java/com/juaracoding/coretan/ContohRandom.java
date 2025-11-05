package com.juaracoding.coretan;


import java.util.Random;

public class ContohRandom {

    public static void main(String[] args) {
        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            System.out.println(r.nextInt(1,11));
        }
        char[] chHurufVokal = "aiueo".toCharArray();
        //5 data , 10 maks 15
        for (int i = 0; i < 5; i++) {
            int intRand = r.nextInt(10,16);
            System.out.print("data ini "+intRand+" digit : ");
            for (int j = 0; j < intRand; j++) {
                System.out.print(chHurufVokal[r.nextInt(0, chHurufVokal.length)]);
            }
            System.out.println();
        }

        /**
         * a,b,c,d,e,f
         * 0-1-2-3-4-5
         * d
         */
    }
}
