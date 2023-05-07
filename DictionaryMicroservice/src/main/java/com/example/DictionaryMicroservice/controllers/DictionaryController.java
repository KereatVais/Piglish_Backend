package com.example.DictionaryMicroservice.controllers;

import com.example.DictionaryMicroservice.dto.WordDTO;
import com.example.DictionaryMicroservice.requests.WordRequest;
import com.example.DictionaryMicroservice.responses.WordResponse;
import com.example.DictionaryMicroservice.services.DictionaryService;
import com.example.DictionaryMicroservice.models.Word;
import com.example.DictionaryMicroservice.util.WordErrorResponse;
import com.example.DictionaryMicroservice.util.WordNotCreatedException;
import com.example.DictionaryMicroservice.util.WordNotFoundException;
import com.example.DictionaryMicroservice.util.WordNotUpdatedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/words")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    public final ModelMapper modelMapper;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService, ModelMapper modelMapper) {
        this.dictionaryService = dictionaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<WordResponse> getWords(@RequestParam("user-id") long userId) {
        return dictionaryService.findAll(userId).stream().map(this::convertToWordResponse).collect(Collectors.toList());
    }

    @GetMapping("/{ids}")
    public List<WordDTO> getRequestedWords(@PathVariable("ids") int[] ids,
                                           @RequestParam("user-id") long userId) {
        return dictionaryService.findAmount(ids, userId).stream().map(this::convertToWordDTO).collect(Collectors.toList());
    }

    @GetMapping("/amount")
    public int getTotalAmount(@RequestParam("user-id") long userId) {
        return dictionaryService.getTotalAmount(userId);
    }

//    @GetMapping("/{id}")
//    public Word getWord(@PathVariable("id") int id) {
//        return dictionaryService.findOne(id);
//    }

    @PostMapping
    public WordResponse create(@RequestBody @Valid WordRequest wordRequest,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new WordNotCreatedException(errorMsg.toString());
        }

        Word newWord = dictionaryService.save(convertToWord(wordRequest));

        return new WordResponse(newWord.getId(), newWord.getWord(), newWord.getTranslation(), newWord.getUserId());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid Word word,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new WordNotUpdatedException(errorMsg.toString());
        }

        dictionaryService.findOne(id);

        dictionaryService.update(id, word);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        dictionaryService.findOne(id);

        dictionaryService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<WordErrorResponse> handleException(WordNotFoundException e) {
        WordErrorResponse response = new WordErrorResponse(
                "Слово с данным айди не было найдено!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<WordErrorResponse> handleException(WordNotCreatedException e) {
        WordErrorResponse response = new WordErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<WordErrorResponse> handleException(WordNotUpdatedException e) {
        WordErrorResponse response = new WordErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Word convertToWord(WordDTO wordDTO) {
        return modelMapper.map(wordDTO, Word.class);
    }

    private Word convertToWord(WordRequest wordRequest) {
        return modelMapper.map(wordRequest, Word.class);
    }

    private WordDTO convertToWordDTO(Word word) {
        return modelMapper.map(word, WordDTO.class);
    }

    private WordResponse convertToWordResponse(Word word) {
        return modelMapper.map(word, WordResponse.class);
    }

//    private List<WordDTO> convertToWordDTOList(Word word) {
//        return modelMapper.map(word, List<>.class);
//    }
}
