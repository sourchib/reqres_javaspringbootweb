package com.juaracoding.controller;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author LENOVO a.k.a. M Muchib Zainul Fikry
Java Developer
Created on 22/10/2025 20:06
@Last Modified 22/10/2025 20:06
Version 1.0
*/

import com.juaracoding.controller.UserController;
import com.juaracoding.service.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Sample data for demonstration
    private List<User> allUsers = Arrays.asList(
            new User(1L, "Fikry","Bekasi"),
            new User(2L, "Kristo","Jakarta"),
            new User(3L, "Andre","Bekasi"),
            new User(4L, "Budi","Bogor"),
            new User(5L, "Ayu","Bogor")
    );

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = allUsers.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
        if(user != null) {
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Search user by name
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(@RequestParam(value = "address", required = false) String address) {
        if (address != null && !address.isEmpty()) {
            List<User> filteredUsers = allUsers.stream()
                    .filter(user -> user.getAddress().toLowerCase().contains(address.toLowerCase()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(filteredUsers);
        }
        return ResponseEntity.ok(allUsers);
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = allUsers.stream().collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}