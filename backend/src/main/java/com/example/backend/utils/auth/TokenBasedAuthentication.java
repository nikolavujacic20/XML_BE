package com.example.backend.utils.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;


public class TokenBasedAuthentication extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 1L;

    private final UserDetails principal;
    @Setter
    @Getter
    private String token;

    public TokenBasedAuthentication(UserDetails principal) {
        super(principal != null ? principal.getAuthorities() : null);
        this.principal = principal;
        super.setAuthenticated(principal != null);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        // Optionally prevent setting this manually to "true" from outside
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted directly. Use constructor instead.");
        }
        super.setAuthenticated(false);
    }
}
