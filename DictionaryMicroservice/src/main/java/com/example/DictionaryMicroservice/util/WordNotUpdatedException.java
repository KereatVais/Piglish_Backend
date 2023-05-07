package com.example.DictionaryMicroservice.util;

public class WordNotUpdatedException extends RuntimeException {
    public WordNotUpdatedException(String msg) {
        super(msg);
    }
}
