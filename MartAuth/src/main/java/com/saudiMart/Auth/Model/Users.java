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

    @Column(nullable = false)
    private String username; // e.g., "Sayeed Ajmal"

    @Column(unique = true, nullable = false)
    private String email; // e.g., "sayeed@example.com"

    @Column(unique = true)
    private String phone_number; // e.g., "+966512345678"

    @Column(nullable = false)
    private String password; // Stored as bcrypt

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
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

}
