package com.example.security.repository;

import javax.xml.crypto.Data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.security.model.SecurityUser;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception{
        SecurityUser user = new SecurityUser();
        user.setUsername("myuser");
        user.setPassword(passwordEncoder.encode("mypassword"));
        user.setRole("USER");
        userRepository.save(user);

        SecurityUser admin = new SecurityUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("adminpass"));
        admin.setRole("ADMIN");
        userRepository.save(admin);
    }
}
