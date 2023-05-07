package com.example.AuthMicroservice.responses;

import java.util.Date;

public class TokenRefreshResponse {
    private String token;
    private Date expirationDate;
    private String refreshToken;
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String token, Date expirationDate, String refreshToken) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.refreshToken = refreshToken;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String accessToken) {
        this.token = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
