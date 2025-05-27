package com.saudiMart.Auth.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String accessToken;

    private String refreshToken;

    private boolean loggedOut;

}