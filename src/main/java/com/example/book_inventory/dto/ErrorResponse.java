package com.example.book_inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;  // Error message
    private String details;  // Optional additional details
}
