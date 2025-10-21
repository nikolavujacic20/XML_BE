package com.example.backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Builder
public class IzvestajRequest {
    private String start;
    private String end;

    @Override
    public String toString() {
        return start.replace('.', '_').concat("__").concat(end.replace('.', '_'));
    }
}
