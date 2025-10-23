package com.juaracoding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:smtpconfig.properties")
public class SMTPConfig {

    @Value("${email.username}")
    private String emailUsername;
}