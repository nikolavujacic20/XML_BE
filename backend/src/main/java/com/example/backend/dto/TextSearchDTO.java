package com.example.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TextSearchDTO {
    private String textSearch;
    private boolean casesensitive;
    private String status;
}
