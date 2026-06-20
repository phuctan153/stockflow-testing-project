package com.stockflow.service;

import com.stockflow.dto.request.LoginRequest;
import com.stockflow.dto.response.LoginResponse;
import com.stockflow.entity.User;
import com.stockflow.exception.BusinessException;
import com.stockflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        validateLoginRequest(request);

        User user = userRepository.findByUsernameIgnoreCase(request.getUsername().trim())
                .orElseThrow(() -> new BusinessException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid username or password");
        }

        String temporaryToken = null;

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole(),
                temporaryToken
        );
    }

    private void validateLoginRequest(LoginRequest request) {
        if (request == null) {
            throw new BusinessException("Login request is required");
        }

        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new BusinessException("Username is required");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new BusinessException("Password is required");
        }
    }
}
