package com.example.demo.controllers;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User authentication endpoints")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    
    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                         JwtUtil jwtUtil,
                         PasswordEncoder passwordEncoder,
                         UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }
    
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // Check if email already exists
            try {
                User existingUser = userService.findByEmail(request.getEmail());
                if (existingUser != null) {
                    return ResponseEntity.badRequest().body("Error: Email is already in use!");
                }
            } catch (RuntimeException e) {
                // User not found, which is good
            }
            
            // Create new user
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole() != null ? request.getRole() : "USER");
            
            // Register user
            User registeredUser = userService.register(user);
            
            // Generate JWT token
            String token = jwtUtil.generateToken(
                registeredUser.getId(),
                registeredUser.getEmail(),
                registeredUser.getRole()
            );
            
            // Create response
            AuthResponse response = new AuthResponse(
                token,
                registeredUser.getEmail(),
                registeredUser.getName(),
                registeredUser.getRole(),
                registeredUser.getId()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            // Get user details
            User user = userService.findByEmail(request.getEmail());
            
            // Generate JWT token
            String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
            
            // Create response
            AuthResponse response = new AuthResponse(
                token,
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getId()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Error: Invalid email or password!");
        }
    }
}