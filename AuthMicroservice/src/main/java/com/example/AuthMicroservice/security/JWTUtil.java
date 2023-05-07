package com.example.AuthMicroservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JWTUtil {
    @Value("${jwtSecret}")
    private String secret;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateToken(UserDetailsImpl userDetails) {
        Date expirationDate = new Date((new Date()).getTime() + jwtExpirationMs);

        return JWT.create().withSubject("User details")
                .withClaim("username", userDetails.getUsername())
                .withIssuedAt(new Date())
                .withIssuer("kate")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateTokenFromUsername(String username) {
        Date expirationDate = new Date((new Date()).getTime() + jwtExpirationMs);

        return JWT.create().withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("kate")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveUsername(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("kate")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

    public Date validateTokenAndRetrieveExpirationDate(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("kate")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getExpiresAt();
    }
}
