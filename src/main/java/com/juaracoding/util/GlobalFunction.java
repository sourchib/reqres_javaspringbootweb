package com.juaracoding.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
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

    public static final String AUTH_HEADERS = "Authorization";


    public static Boolean checkValue(String value,String pattern){
        Boolean isValid = Pattern.compile(pattern).matcher(value).find();
        return isValid;
    }

    public static Map<String,Object> convertClassToMap(Object object){
        Map<String,Object> map = new LinkedHashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();//Reflection
        for(Field field : fields){
            field.setAccessible(true);
            try{
                map.put(field.getName(),field.get(object));
            }catch(Exception e){

            }
        }
        return map;
    }

    public static String camelToStandard(String camel){
        StringBuilder sb = new StringBuilder();
        char c = camel.charAt(0);
        sb.append(Character.toLowerCase(c));
        for (int i = 1; i < camel.length(); i++) {
            char c1 = camel.charAt(i);
            if(Character.isUpperCase(c1)){
                sb.append(' ').append(Character.toLowerCase(c1));
            }
            else {
                sb.append(c1);
            }
        }
        return sb.toString();
    }
}
