//package com.example.DictionaryMicroservice;
//
//import com.example.DictionaryMicroservice.models.Word;
//import com.example.DictionaryMicroservice.repository.DictionaryRepository;
//import com.example.DictionaryMicroservice.services.DictionaryService;
//import com.example.DictionaryMicroservice.util.WordNotFoundException;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.*;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@DataJpaTest
////@Sql(scripts = "/create-data.sql")
////@Sql(scripts = "/clean-data.sql", executionPhase = AFTER_TEST_METHOD)
//class RestDemoApplicationTests {
//
//	@Autowired
//	private DictionaryService dictionaryService;
//
//	@Autowired
//	private DictionaryRepository dictionaryRepository;
//
//	@BeforeEach
//	public void clearDb() {
//		dictionaryRepository.deleteAll();
//	}
//
//	@Test
//	void whenWordExists_thenFindByIdReturnsNotNull() {
//		Word savedWord = dictionaryService.save(new Word("Lion", "Лев"));
//		Word foundWord = dictionaryService.findOne(savedWord.getId());
//
//		assertNotNull(foundWord);
//	}
//
//	@Test
//	void whenSaveWord_thenSavedWordIsNotNull_andPropsEqualsWordProps() {
//		Word savedWord = dictionaryService.save(
//				new Word("Flower", "Цветок"));
//
//		Word foundWord = dictionaryService.findOne(savedWord.getId());
//
//		assertNotNull(foundWord);
//		assertEquals(savedWord.getWord(), foundWord.getWord());
//		assertEquals(savedWord.getTranslation(), foundWord.getTranslation());
//	}
//
//	@Test
//	void whenDeleteAll_thenTableIsEmpty() {
//		dictionaryRepository.deleteAll();
//		assertThat(dictionaryService.findAll().isEmpty());
//	}
//
//	@Test
//	void whenDeleteWord_thenThereIsNoWordWithThisId() {
//		Word word = dictionaryService.save(new Word("Flower", "Цветок"));
//		dictionaryService.delete(word.getId());
//
//		assertThrows(WordNotFoundException.class, () -> dictionaryService.findOne(word.getId()));
//	}
//
//	@Test
//	void whenDeleteWordTwice_thenThereIsAnException() {
//		Word word = dictionaryService.save(new Word("Flower", "Цветок"));
//		dictionaryService.delete(word.getId());
//
//		assertThrows(Exception.class, () -> dictionaryService.delete(word.getId()));
//	}
//
//	@Test
//	void whenDeleteWordWithIdThatDoesntExist_thenThereIsAnException() {
//		assertThrows(Exception.class, () -> dictionaryService.delete(2));
//	}
//
//	@Test
//	void whenAddAmountOfWords_thenExpectFindAllRetrieveAddedAmountOfWords() {
//		dictionaryService.save(new Word("Monkey", "Обезьяна"));
//		dictionaryService.save(new Word("Tiger", "Тигр"));
//		dictionaryService.save(new Word("Butterfly", "Бабочка"));
//
//		List<Word> words = dictionaryService.findAll();
//
//		assertThat(words.size() == 3);
//	}
//
//	@Test
//	void whenAddAmountOfWords_thenExpectGetAmountRetrieveAddedAmountOfWords() {
//		dictionaryService.save(new Word("Monkey", "Обезьяна"));
//		dictionaryService.save(new Word("Tiger", "Тигр"));
//		dictionaryService.save(new Word("Butterfly", "Бабочка"));
//
//		int amount = dictionaryService.getTotalAmount();
//
//		assertThat(amount == 3);
//	}
//
//	@Test
//	void whenUpdateWord_thenRetrieveNewWord() {
//		Word savedWord = dictionaryService.save(new Word("monkey", "обезьяна"));
//
//		Word newWord = new Word("moon", "луна");
//		dictionaryService.update(savedWord.getId(), newWord);
//
//		Word updatedWord = dictionaryService.findOne(savedWord.getId());
//		assertEquals(newWord.getWord(), updatedWord.getWord());
//		assertEquals(newWord.getTranslation(), updatedWord.getTranslation());
//	}
//
//	@Test
//	void whenDbIsEmpty_thenIsImpossibleToDeleteAWord() {
//		assertThrows(Exception.class, () -> dictionaryService.delete(1));
//	}
//
//	@Test
//	void whenDbIsEmpty_thenIsImpossibleToFindAWord() {
//		assertThrows(WordNotFoundException.class, () -> dictionaryService.findOne(1));
//	}
//
//	@Test
//	void whenFindAmount_thenGetRequestedAmount() {
//		Word word1 = dictionaryService.save(new Word("111", "111"));
//		Word word2 = dictionaryService.save(new Word("222", "222"));
//		int[] idxs = new int[] {word1.getId(), word2.getId()};
//
//		List<Word> words = dictionaryService.findAmount(idxs);
//
//		assertEquals(2, words.size());
//	}
//
//	@Test
//	void whenFindAmount_thenGetRequestedWords() {
//		Word word1 = dictionaryService.save(new Word("111", "111"));
//		Word word2 = dictionaryService.save(new Word("222", "222"));
//		int[] idxs = new int[] {word1.getId(), word2.getId()};
//
//		List<Word> words = dictionaryService.findAmount(idxs);
//
//		assertEquals(word1.getWord(), words.get(0).getWord());
//		assertEquals(word1.getTranslation(), words.get(0).getTranslation());
//		assertEquals(word2.getWord(), words.get(1).getWord());
//		assertEquals(word2.getTranslation(), words.get(1).getTranslation());
//	}
//}
