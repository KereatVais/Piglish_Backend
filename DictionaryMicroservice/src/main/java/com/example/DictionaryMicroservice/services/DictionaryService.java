package com.example.DictionaryMicroservice.services;

import com.example.DictionaryMicroservice.models.Word;
import com.example.DictionaryMicroservice.repository.DictionaryRepository;
import com.example.DictionaryMicroservice.util.WordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public List<Word> findAll(long userId) {
        return dictionaryRepository.findAll().stream().filter(word -> word.getUserId() == userId).toList();
    }

    public int getTotalAmount(long userId) {
        return findAll(userId).size();
    }
    public List<Word> findAmount(int[] indexes, long userId) {
        List<Word> words = findAll(userId);
        List<Word> wordsToReturn = new ArrayList<>();

        int amount = words.size();

        int cnt = 0;
        for (int i = 0; i < amount; i++) {
            if (i == indexes[cnt]) {
                wordsToReturn.add(words.get(i));

                if (cnt != indexes.length - 1)
                    cnt++;
            }
        }

        return wordsToReturn;
    }

    public Word findOne(int id) {
        Optional<Word> word = dictionaryRepository.findById(id);
        return word.orElseThrow(WordNotFoundException::new);
    }

    @Transactional
    public Word save(Word word) {
        word.setCreatedAt(LocalDateTime.now());
        word.setUpdatedAt(LocalDateTime.now());
        return dictionaryRepository.save(word);
    }

    @Transactional
    public void update(int id, Word word) {
        word.setUpdatedAt(LocalDateTime.now());
        dictionaryRepository.setWordById(word.getWord(), word.getTranslation(), word.getUserId(), id);
    }

    @Transactional
    public void delete(int id) {
        dictionaryRepository.deleteById(id);
    }
}
