package com.example.AuthMicroservice.responses;

import java.util.Date;
import java.util.List;

public class JwtResponse {
    private String token;
    private Date expirationDate;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private List<String> roles;

    public JwtResponse(String accessToken, Date expirationDate, String refreshToken, Long id, String username, List<String> roles) {
        this.token = accessToken;
        this.expirationDate = expirationDate;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
