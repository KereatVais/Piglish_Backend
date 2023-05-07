package com.example.DictionaryMicroservice.repository;

import com.example.DictionaryMicroservice.models.Word;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends JpaRepository<Word, Integer> {
//    Word findById(int id);
    @Modifying
    @Query("update Word w set w.word = ?1, w.translation = ?2, w.userId = ?3 where w.id = ?4")
    void setWordById(String word, String translation, long userId, int id);
}
