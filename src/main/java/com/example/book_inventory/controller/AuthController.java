package com.example.book_inventory.controller;

import com.example.book_inventory.dto.LoginRequest;
import com.example.book_inventory.dto.RegisterRequest;
import com.example.book_inventory.dto.AuthResponse;
import com.example.book_inventory.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Register endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        try {
            String message = authService.registerUser(request);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.authenticateUser(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new AuthResponse(ex.getMessage(), null, null, null));
        }
    }
}
