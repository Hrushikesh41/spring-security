package com.example.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
    private Set<String> blacklistedToken = new HashSet<>();

    public void blacklistToken(String token) {
        blacklistedToken.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedToken.contains(token);
    }
}
