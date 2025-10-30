package com.juaracoding.config;


import com.juaracoding.security.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Random;

@Configuration
public class MainConfig {

    @Autowired
    private Environment env;

    @Bean
    public Random getRandom(){
        return new Random();
    }

    @Primary
    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(Crypto.performDecrypt(env.getProperty("spring.datasource.url")));
        dataSourceBuilder.username(Crypto.performDecrypt(env.getProperty("spring.datasource.username")));
        dataSourceBuilder.password(Crypto.performDecrypt(env.getProperty("spring.datasource.password")));
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));

        return dataSourceBuilder.build();
    }




}
