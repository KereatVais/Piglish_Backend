package com.example.DictionaryMicroservice.util;

public class WordNotCreatedException extends RuntimeException {
    public WordNotCreatedException(String msg) {
        super(msg);
    }
}
