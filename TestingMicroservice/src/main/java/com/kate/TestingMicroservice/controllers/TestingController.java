package com.kate.TestingMicroservice.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/api/testing")
public class TestingController {
    private final WebClient.Builder webClientBuilder;

    public TestingController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping()
    public String getRequestedWords(@RequestParam("method") String method,
                                    @RequestParam("amount") int amount,
                                    @RequestParam("user-id") long userId) {
        int[] indexes = new int[amount];

        String amountURL = "http://dictionary-microservice/api/words/amount?user-id=" + userId;
//        int size = restTemplate.getForObject(amountURL, Integer.class);
        int size = webClientBuilder.build().get().uri(amountURL).retrieve().bodyToMono(Integer.class).block();

        if (method.equals("random")) {
            indexes = getRandomIndexes(amount, size);
        } else if (method.equals("last")) {
            indexes = getLastIndexes(amount, size);
        }

        StringBuilder stringBuilderIndexes = new StringBuilder();
        for (int i : indexes) {
            stringBuilderIndexes.append(i).append(",");
        }
        stringBuilderIndexes.deleteCharAt(stringBuilderIndexes.length() - 1);
        String indexesForRequest = stringBuilderIndexes.toString();
        String wordsURL = "http://dictionary-microservice/api/words/" + indexesForRequest + "?user-id=" + userId;
        return webClientBuilder.build().get().uri(wordsURL).retrieve().bodyToMono(String.class).block();
    }

    private int[] getRandomIndexes(int amount, int maxSize) {
        int[] indexes = new int[amount];

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < maxSize; i++)
            list.add(i);
        Collections.shuffle(list);
        for (int i = 0; i < amount; i++)
            indexes[i] = list.get(i);
        Arrays.sort(indexes);

        return indexes;
    }

    private int[] getLastIndexes(int amount, int maxSize) {
        int[] indexes = new int[amount];

        for (int i = maxSize - 1; i >= maxSize - amount; i--) {
            indexes[maxSize - i - 1] = i;
        }
        Arrays.sort(indexes);

        return indexes;
    }
}
