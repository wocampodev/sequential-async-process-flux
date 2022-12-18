package com.example.repository;

import com.example.model.entity.Example;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ExampleRepository extends R2dbcRepository<Example, Long> {
    @Query(value = """
            SELECT *
            FROM example
            WHERE word_id=:wordId
            LIMIT :limit
            """)
    Flux<Example> findExamples(int limit, Long wordId);
}
