package com.example.service;

import com.example.model.dto.WrapperResponse;
import com.example.model.entity.Example;
import com.example.model.entity.Word;
import com.example.repository.ExampleRepository;
import com.example.repository.WordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/")
@Slf4j
public class AppService {
    private final WordRepository wordRepository;
    private final ExampleRepository exampleRepository;

    @Autowired
    public AppService(WordRepository wordRepository, ExampleRepository exampleRepository) {
        this.wordRepository = wordRepository;
        this.exampleRepository = exampleRepository;
    }

    @GetMapping(value = "/words")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Word> findWords(@RequestParam(defaultValue = "10") int limit) {
        return wordRepository.findWords(limit)
                .map(this::fillData);
    }

    @GetMapping(value = "/examples/{wordId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Example> findExamples(@RequestParam(defaultValue = "10") int limit, @PathVariable Long wordId) {
        return getExamplesByWordId(wordId, limit);
    }

    @GetMapping(value = "/solution")
    @ResponseStatus(HttpStatus.OK)
    public Flux<WrapperResponse> findSolution(@RequestParam(defaultValue = "10") int limit) {
        // curl http://localhost:9090/solution?limit=5 | jq .
        // http http://localhost:9090/solution?limit=5
        log.info(":: Start processing ::");
        return wordRepository.findWords(limit)
                .map(this::fillData)
                .flatMap(word -> {
                    return getExamplesByWordId(word.getId(), limit)
                            .collectList()
                            .map(exampleList -> {
                                log.info(":: End processing ::");
                                return WrapperResponse.builder()
                                        .wordId(word.getId())
                                        .text(word.getText())
                                        .computedData(word.getComputedData())
                                        .examples(exampleList)
                                        .build();
                            });
                });
    }

    private Flux<Example> getExamplesByWordId(Long wordId, int limit) {
        return exampleRepository.findExamples(limit, wordId);
    }

    private Word fillData(Word word) {
        String encoded = new BCryptPasswordEncoder().encode(word.getText());
        word.setComputedData(encoded);
        return word;
    }
}
