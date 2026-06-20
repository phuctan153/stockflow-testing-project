package com.stockflow.config;

import com.stockflow.entity.User;
import com.stockflow.enums.Role;
import com.stockflow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createUserIfNotExists(
                "admin",
                "123456",
                "StockFlow Admin",
                Role.ADMIN
        );

        createUserIfNotExists(
                "staff",
                "123456",
                "StockFlow Staff",
                Role.STAFF
        );
    }

    private void createUserIfNotExists(String username, String rawPassword, String fullName, Role role) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            return;
        }

        User user = new User(
                username,
                passwordEncoder.encode(rawPassword),
                fullName,
                role
        );

        userRepository.save(user);
    }
}
