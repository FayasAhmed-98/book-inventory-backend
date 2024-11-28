package com.example.book_inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private String username;
    private String role;
    private String token; // Include JWT token for use in the client

    // Constructor for cases where no token and role are provided
    public AuthResponse(String message, String username) {
        this.message = message;
        this.username = username;
        this.role = null;  // No role in error messages
        this.token = null; // No token in error messages
    }

    // Constructor for error responses (with message only)
    public AuthResponse(String message) {
        this.message = message;
        this.username = null;
        this.role = null;
        this.token = null;
    }
}

