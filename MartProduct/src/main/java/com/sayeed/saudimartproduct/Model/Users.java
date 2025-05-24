package com.sayeed.saudimartproduct.Model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class Users implements UserDetails {

    private String userId;

    private String username;

    private String email;

    private String phone_number;

    private String password;

    private String role;

    private Boolean isVerified;

    private Timestamp createdAt;

    private boolean enabled = true;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

}
