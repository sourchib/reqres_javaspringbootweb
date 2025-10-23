package com.juaracoding.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class MainConfig {

    @Bean
    public Random getRandom(){
        return new Random();
    }
}
