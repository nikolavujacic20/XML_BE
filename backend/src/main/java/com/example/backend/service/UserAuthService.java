package com.example.backend.service;

import com.example.backend.model.UserAuth;
import com.example.backend.repository.UserAuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserAuthService {
    private final UserAuthRepository userAuthRepository;

    public UserAuth save(UserAuth userAuth){
        return userAuthRepository.save(userAuth);
    }
}
