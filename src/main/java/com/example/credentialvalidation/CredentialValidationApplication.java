package com.example.credentialvalidation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.credentialvalidation.model.AppUser;
import com.example.credentialvalidation.repository.UserRepository;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class CredentialValidationApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CredentialValidationApplication(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(CredentialValidationApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("student").isEmpty()) {
            AppUser user = new AppUser();
            user.setUsername("student");
            user.setPasswordHash(passwordEncoder.encode("Lab@123"));
            userRepository.save(user);
        }
    }
}
