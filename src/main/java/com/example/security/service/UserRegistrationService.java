package com.example.security.service;

import com.example.security.model.SecurityUser;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SecurityUser registerUser(String username, String password, String role) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (role == null || (!role.equals("USER") && !role.equals("ADMIN"))) {
            throw new IllegalArgumentException("Invalid role. Must be USER or ADMIN");
        }
        SecurityUser user = new SecurityUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }
}