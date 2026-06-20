package com.stockflow.dto.response;

import com.stockflow.enums.Role;

public class LoginResponse {

    private Long userId;
    private String username;
    private String fullName;
    private Role role;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(Long userId, String username, String fullName, Role role, String token) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public Role getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
