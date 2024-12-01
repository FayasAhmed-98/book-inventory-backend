package com.example.book_inventory.repository;

import com.example.book_inventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username or email
    Optional<User> findByUsername(String username);

    // Check if the username already exists
    boolean existsByUsername(String username);

    // Check if the email already exists
    boolean existsByEmail(String email);
}
