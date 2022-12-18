package com.example.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Example {
    @Id
    private Long id;

    private String name;

    private String wordId;
}
