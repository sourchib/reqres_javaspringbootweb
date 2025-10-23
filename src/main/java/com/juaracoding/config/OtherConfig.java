package com.juaracoding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:other.properties")
public class OtherConfig {

    private static String enableLogFile;

    public static String getEnableLogFile() {
        return enableLogFile;
    }

    @Value("${enable.log.file}")
    private void setEnableLogFile(String enableLogFile) {
        this.enableLogFile = enableLogFile;
    }
}
