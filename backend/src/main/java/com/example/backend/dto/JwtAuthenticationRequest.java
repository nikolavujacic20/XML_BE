package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JwtAuthenticationRequest {
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
