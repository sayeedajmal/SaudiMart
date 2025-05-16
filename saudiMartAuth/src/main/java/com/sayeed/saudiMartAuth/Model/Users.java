package com.sayeed.saudiMartAuth.Model;

import java.sql.Timestamp;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId; // Auto-generated

    @Column(nullable = false)
    private String name; // e.g., "Sayeed Ajmal"

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

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
