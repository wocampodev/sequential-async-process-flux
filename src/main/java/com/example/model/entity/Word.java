package com.example.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Word {

    @Id
    private Long id;

    private String text;

    private String computedData;

}
