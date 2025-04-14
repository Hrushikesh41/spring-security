package com.example.security.controller;

import com.example.security.service.*;
import com.example.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRegistrationService registrationService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenBlacklistService blacklistService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid credentials"));
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String role = userDetails.getAuthorities().iterator().next().getAuthority();
        System.out.println("Login role: " + role);
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), role);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            registrationService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getRole());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(registerRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername(), "ROLE_" + registerRequest.getRole());
            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/logout")
public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        String jwt = authorizationHeader.substring(7);
        blacklistService.blacklistToken(jwt);
        return ResponseEntity.ok("Logged out successfully");
    }
    return ResponseEntity.badRequest().body(new ErrorResponse("Invalid or missing token"));
}
}

class LoginRequest {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

class RegisterRequest {
    private String username;
    private String password;
    private String role;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

class LoginResponse {
    private String token;

    public LoginResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}

class ErrorResponse {
    private String message;

    public ErrorResponse(String message) { this.message = message; }
    public String getMessage() { return message; }
}