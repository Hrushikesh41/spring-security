package com.example.security.service;

import com.example.security.model.SecurityUser;
import com.example.security.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = userRepository.findByUsername(username);
        if (securityUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        String role = securityUser.getRole().startsWith("ROLE_") ? securityUser.getRole() : "ROLE_" + securityUser.getRole();
        System.out.println("UserDetails role for " + username + ": " + role);
        return User
            .withUsername(securityUser.getUsername())
            .password(securityUser.getPassword())
            .authorities(role)
            .build();
    }
}