package com.example.DictionaryMicroservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "Dictionary")
public class Word {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "word")
    @NotEmpty(message = "Слово не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина слова должны быть от 2-х до 30-ти символов")
    private String word; // word - English version

    @Column(name = "translation")
    @NotEmpty(message = "Перевод не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина перевода должны быть от 2-х до 30-ти символов")
    private String translation; // translation - Russian version

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "user_id")
    @NotNull(message = "Айди пользователя не может быть пустым")
    private long userId;

    public Word() {
    }

    public Word(String word, String translation, long userId) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
