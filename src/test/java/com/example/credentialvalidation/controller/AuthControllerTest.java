
package com.example.credentialvalidation.controller;

import java.util.Optional;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.credentialvalidation.dto.LoginRequest;
import com.example.credentialvalidation.model.AppUser;
import com.example.credentialvalidation.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    UserRepository repo;

    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    AuthController controller;

    @Test
    void validLogin() {
        AppUser user = new AppUser();
        user.setUsername("student");
        user.setPasswordHash("hashed");

        when(repo.findByUsername("student"))
            .thenReturn(Optional.of(user));
        when(encoder.matches("Lab@123", "hashed"))
            .thenReturn(true);

        LoginRequest req = new LoginRequest();
        req.setUsername("student");
        req.setPassword("Lab@123");

        ResponseEntity<Map<String, String>> res =
            controller.login(req);

        assertEquals(200, res.getStatusCode().value());
    }

    @Test
    void invalidLogin() {
        when(repo.findByUsername("student"))
            .thenReturn(Optional.empty());

        LoginRequest req = new LoginRequest();
        req.setUsername("student");
        req.setPassword("wrong");

        ResponseEntity<Map<String, String>> res =
            controller.login(req);

        assertEquals(401, res.getStatusCode().value());
    }
}
