package com.example.backend.controller;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.JwtAuthenticationRequest;
import com.example.backend.dto.LoginResponseDTO;
import com.example.backend.dto.UserResponse;
import com.example.backend.model.User;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.DTOMapper;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping(
            path = "/login",
            consumes = "application/xml",
            produces = "application/xml"
    )
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody JwtAuthenticationRequest request) {
        LoginResponseDTO result = authenticationService.login(request);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(
            path = "/register",
            consumes = "application/xml",
            produces = "application/xml"
    )
    public ResponseEntity<Void> register(@Valid @RequestBody CreateUserDTO dto) {
        User created = authenticationService.addUser(dto);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(
            path = "/currently-logged-user",
            produces = "application/xml"
    )
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        User user = userService.getLoggedUser(authentication);
        if (user == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(DTOMapper.toUserResponse(user));
    }
}
