package com.example.book_inventory.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., "ADD_BOOK", "DELETE_BOOK", "VIEW_BOOK"
}
