package com.example.book_inventory.service;

import com.example.book_inventory.model.User;
import com.example.book_inventory.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Search for user by username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        // Build UserDetails object for Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername()) // Use username for principal
                .password(user.getPassword()) // Encoded password
                .authorities("ROLE_" + user.getRole().name()) // Add role as a granted authority
                .build();
    }
}
