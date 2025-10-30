package com.juaracoding.config;
/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 29/10/2025 20:03
@Last Modified 29/10/2025 20:03
Version 1.0
*/

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    /*
        DEFAULT URL Untuk mengakses SWAGGER http://localhost:8033/swagger-ui/index.html
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
//                .components(new Components().
//                        addSecuritySchemes("Bearer Authentication", createAPIKeyScheme())
//                )
                .info(new Info().title("Springboot+JPA+JWT+SQLServer")
                        .description("SPRINGBOOT REST API")
                        .version("1.0").contact(new Contact().name("Juara Coding Dev").email( "jcoding@juaracoding.com")
                                .url("localhost:8033"))
                        .license(new License().name("Springboot Open Source License")
                                .url("https://spring.io/")));
    }

//    private SecurityScheme createAPIKeyScheme() {
//        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
//                .bearerFormat("JWT")
//                .scheme("bearer");
//    }
}


