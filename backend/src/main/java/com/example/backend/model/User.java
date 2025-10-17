package com.example.backend.model;

import com.example.backend.model.enums.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

/**
 * Entity representing a system user with authentication details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String name;
    private String surname;
    private String phoneNumber;

    @Builder.Default
    private Boolean deleted = false;

    @OneToOne
    private UserAuth userAuth;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuth != null ? userAuth.getRoles() : null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userAuth != null && userAuth.getIsEnabled();
    }
}
