package com.example.AuthMicroservice.requests;

import javax.validation.constraints.NotBlank;

public class TokenLoginRequest {
    @NotBlank
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
