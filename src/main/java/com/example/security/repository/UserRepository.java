package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.model.SecurityUser;

@Repository
public interface UserRepository extends JpaRepository<SecurityUser, String> {
    SecurityUser findByUsername(String username);
}
