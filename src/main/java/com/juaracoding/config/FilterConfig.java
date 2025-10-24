package com.juaracoding.config;

import com.juaracoding.security.JavaFilterz;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<JavaFilterz> customHttpFilter() {
        FilterRegistrationBean<JavaFilterz> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JavaFilterz());
        registrationBean.addUrlPatterns("/*"); // Atur pola URL untuk disaring
        registrationBean.setOrder(1); // Atur urutan filter jika ada beberapa

        return registrationBean;
    }
}