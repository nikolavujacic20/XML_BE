package com.example.backend.service;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.JwtAuthenticationRequest;
import com.example.backend.dto.LoginResponseDTO;
import com.example.backend.dto.UserTokenState;
import com.example.backend.model.User;
import com.example.backend.model.UserAuth;
import com.example.backend.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserService userService;
    private final TokenUtils tokenUtils;
    private final UserAuthService userAuthService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public User addUser(CreateUserDTO dto) {
        if (userService.findByEmail(dto.getEmail()) != null) {
            return null;
        }
        return saveUser(dto);
    }

    private User saveUser(CreateUserDTO dto) {
        User user = DTOMapper.toUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserAuth(createUserAuth());
        return userService.saveUser(user);
    }

    private UserAuth createUserAuth() {
        UserAuth ua = new UserAuth();
        return userAuthService.save(ua);
    }

    public LoginResponseDTO login(JwtAuthenticationRequest req) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
        } catch (AuthenticationException ex) {
            return null;
        }
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = (User) auth.getPrincipal();
        return buildLoginResponse(user);
    }

    private LoginResponseDTO buildLoginResponse(User user) {
        String jwt = tokenUtils.generateToken(user);
        long expiresIn = tokenUtils.getExpiredIn();
        return new LoginResponseDTO(new UserTokenState(jwt, expiresIn), user.getRole());
    }
}
