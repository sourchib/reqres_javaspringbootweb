package com.juaracoding.controller;

import com.juaracoding.dto.validation.LoginDTO;
import com.juaracoding.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 22/10/2025 21:38
@Last Modified 22/10/2025 21:38
Version 1.0
*/
// auth/**
// auth/login
// auth/regis
// auth/forgotpassword
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO,
                                        HttpServletRequest request){
        return authService.login(authService.mapToUser(loginDTO),request);
    }

}
