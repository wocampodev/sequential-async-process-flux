package com.example.repository;

import com.example.model.entity.Word;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface WordRepository extends R2dbcRepository<Word, Long> {

    @Query(value = """
            SELECT id, text
            FROM word
            LIMIT :limit
            """)
    Flux<Word> findWords(int limit);

}
