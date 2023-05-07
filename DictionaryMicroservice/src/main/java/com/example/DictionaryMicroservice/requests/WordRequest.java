package com.example.DictionaryMicroservice.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WordRequest {
    @NotEmpty(message = "Слово не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина слова должны быть от 2-х до 30-ти символов")
    private String word; // word - English version

    @NotEmpty(message = "Перевод не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина перевода должны быть от 2-х до 30-ти символов")
    private String translation; // translation - Russian version

    @NotNull(message = "Айди пользователя не может быть пустым")
    private long userId;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
