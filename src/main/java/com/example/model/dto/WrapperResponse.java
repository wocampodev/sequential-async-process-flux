package com.example.model.dto;

import com.example.model.entity.Example;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WrapperResponse {
    private Long wordId;

    private String text;

    private String computedData;
    private List<Example> examples;
}
