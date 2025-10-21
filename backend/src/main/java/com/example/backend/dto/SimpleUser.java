package com.example.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Builder
public class SimpleUser {
    private String name;
    private String email;
}
