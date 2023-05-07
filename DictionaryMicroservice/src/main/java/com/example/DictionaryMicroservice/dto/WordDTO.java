package com.example.DictionaryMicroservice.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class WordDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Word can't be empty")
    @Size(min = 2, max = 30, message = "Word's length should be between 2 and 30 symbols")
    private String word; // word - English version

    @NotEmpty(message = "Translation can't be empty")
    @Size(min = 2, max = 30, message = "Translation's length should be between 2 and 30 symbols")
    private String translation; // translation - Russian version

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
