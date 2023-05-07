package com.example.DictionaryMicroservice.responses;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WordResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Слово не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина слова должны быть от 2-х до 30-ти символов")
    private String word; // word - English version

    @NotEmpty(message = "Перевод не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина перевода должны быть от 2-х до 30-ти символов")
    private String translation; // translation - Russian version

    @NotNull(message = "Айди пользователя не может быть пустым")
    private long userId;

    public WordResponse() {
    }

    public WordResponse(int id, String word, String translation, long userId) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
