package com.example.backend.service;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.UserResponse;
import com.example.backend.model.User;

public final class DTOMapper {

    private DTOMapper() {}

    public static UserResponse toUserResponse(User user) {
        return user == null ? null : UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public static User toUser(CreateUserDTO dto) {
        return dto == null ? null : User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .role(dto.getUserRole())
                .build();
    }
}
