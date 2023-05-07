package com.example.AuthMicroservice.util;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String msg) {
        super(msg);
    }
}
