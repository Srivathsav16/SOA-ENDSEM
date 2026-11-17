package com.example.credentialvalidation.controller;

import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.credentialvalidation.repository.UserRepository;
import com.example.credentialvalidation.dto.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @PostMapping("/login-db")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginRequest req) {

        var user = repo.findByUsername(req.getUsername()).orElse(null);

        if (user != null && encoder.matches(
                req.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.ok(
                Map.of("message", "Authentication Successful"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            Map.of("message", "Authentication Failed"));
    }
}