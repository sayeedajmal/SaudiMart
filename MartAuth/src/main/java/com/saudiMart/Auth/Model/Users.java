package com.saudiMart.Auth.Model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId = UUID.randomUUID().toString();

    @Column(name = "name", nullable = false)
    private String name; // e.g., "Sayeed Ajmal"

    @Column(unique = true, nullable = false)
    private String email; // e.g., "sayeed@example.com"

    @Column(name = "phone_number", unique = true)
    private String phoneNumber; // e.g., "+966512345678"

    @Column(name = "password", nullable = false)
    private String password; // Stored as bcrypt

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String role; // e.g., "BUYER" or "SELLER" or "ADMIN"

    @Column(nullable = false)
    private Boolean isVerified; // After email or OTP verified

    @CreationTimestamp
    private Timestamp createdAt;

    private boolean enabled = true;

    // Spring Security flags — defaults should be set during registration
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email; // Using email as the username for UserDetails
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
