package com.juaracoding.controller;

import com.juaracoding.dto.validation.LoginDTO;
import com.juaracoding.dto.validation.RegisDTO;
import com.juaracoding.dto.validation.VerifyRegisDTO;
import com.juaracoding.security.AESGeneratedKey;
import com.juaracoding.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Auth", description = "Authentication and Authorization APIs")
public class AuthController {
    /**
     * 1. Registrasi
     * 2. Login
     * 3. Lupa Password
     */
    @Autowired
    AuthService authService;

    @Operation(summary = "User Registration", description = "Registers a new user in the system.")
    @ApiResponse(responseCode = "200", description = "Registration successful")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping("/regis")
    public ResponseEntity<Object> registration(@Valid @RequestBody RegisDTO regisDTO
            , HttpServletRequest request

    ){
        return authService.regis(authService.mapToUser(regisDTO),request);
    }

    @Operation(summary = "Verify Registration", description = "Verifies a user's registration using a verification token.")
    @ApiResponse(responseCode = "200", description = "Verification successful")
    @ApiResponse(responseCode = "400", description = "Invalid token or user already verified")
    @PostMapping("/verify-regis")
    public ResponseEntity<Object> verifyRegis(@Valid @RequestBody VerifyRegisDTO verifyRegisDTO
            , HttpServletRequest request){
        return authService.verifyRegis(authService.mapToUser(verifyRegisDTO),request);
    }

    @Operation(summary = "User Login", description = "Authenticates a user and returns an authentication token.")
    @ApiResponse(responseCode = "200", description = "Login successful, returns JWT token")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO
            , HttpServletRequest request){
        return authService.login(authService.mapToUser(loginDTO),request);
    }

    @Operation(summary = "Refresh Token", description = "Refreshes an expired authentication token.")
    @ApiResponse(responseCode = "200", description = "Token refreshed successfully")
    @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    @PostMapping("/refresh-token")
    public ResponseEntity<Object> tokenExpired(@Valid @RequestBody LoginDTO loginDTO
            , HttpServletRequest request){
        return authService.refreshToken(authService.mapToUser(loginDTO),request);
    }

    @Operation(summary = "Generate AES Key", description = "Generates a new AES encryption key.")
    @ApiResponse(responseCode = "200", description = "Key generated successfully")
    @GetMapping("/gen-key")
    public String tokenExpired(){
        return "Your Key : "+AESGeneratedKey.getKey();
    }
}