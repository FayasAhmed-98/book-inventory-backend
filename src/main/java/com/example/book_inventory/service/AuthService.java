package com.example.book_inventory.service;

import com.example.book_inventory.dto.AuthResponse;
import com.example.book_inventory.dto.LoginRequest;
import com.example.book_inventory.dto.RegisterRequest;
import com.example.book_inventory.exception.InvalidCredentialsException;
import com.example.book_inventory.exception.UserAlreadyExistsException;
import com.example.book_inventory.model.User;
import com.example.book_inventory.model.Role;
import com.example.book_inventory.repository.RoleRepository;
import com.example.book_inventory.repository.UserRepository;
import com.example.book_inventory.security.CustomUserDetails;
import com.example.book_inventory.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    /**
     * Registers a new user.
     *
     * @param registerRequest The user registration request containing username, email, and password.
     * @return A success message with the assigned role.
     */
    public String registerUser(RegisterRequest registerRequest) {
        // Check if the username or email is already taken
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken.");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use.");
        }

        // Determine the role based on the email domain
        String roleName = registerRequest.getEmail().endsWith("@azendtech.com") ? "ADMIN" : "USER";

        // Fetch the Role entity from the database
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        // Create a new User object and populate its fields
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Encrypt the password
        user.setRole(role); // Assign the role

        // Save the User to the database
        userRepository.save(user);

        return "User registered successfully with role: " + role.getName();
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param authRequest The login request containing username and password.
     * @return An AuthResponse containing the JWT token and user details.
     */
    public AuthResponse authenticateUser(LoginRequest authRequest) {
        // Try to find user by username or email
        User user = userRepository.findByUsernameOrEmail(authRequest.getUsername(), authRequest.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        // Check if the provided password matches the stored hashed password
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Generate a JWT token
        String jwt = jwtTokenUtil.generateToken(new CustomUserDetails(user));

        // Return a success response with user details and the token
        return new AuthResponse(
                "Login successful",
                user.getUsername(),
                user.getRole().getName(),
                jwt
        );
    }

}

