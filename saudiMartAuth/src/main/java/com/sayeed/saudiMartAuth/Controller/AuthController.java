package com.sayeed.saudiMartAuth.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sayeed.saudiMartAuth.Model.ResponseWrapper;
import com.sayeed.saudiMartAuth.Model.Users;
import com.sayeed.saudiMartAuth.Service.UserService;
import com.sayeed.saudiMartAuth.Utils.UserException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * Handles user registration.
     * 
     * @param user the user object from the request body
     * @return a response entity containing the JWT access token and refresh token
     * @throws UserException if the email is already taken
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> signUp(@RequestBody Users user) throws UserException {
        Map<String, Object> tokens = userService.signUp(user);
        return ResponseEntity.ok(new ResponseWrapper<>(201, "User registered successfully", tokens));
    }

    @PostMapping("/login")
    /**
     * Handles user login.
     * 
     * @param loginRequest a map containing the "email" and "password" fields from
     *                     the request body
     * @return a response entity containing the JWT access token and refresh token
     * @throws UserException if the authentication fails
     */
    public ResponseEntity<ResponseWrapper<?>> authenticate(
            @RequestBody Map<String, String> loginRequest) throws UserException {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        Map<String, Object> tokens = userService.authenticate(email, password);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Login successful", tokens));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseWrapper<Map<String, String>>> refreshToken(
            @RequestBody Map<String, String> request) throws UserException {
        String refreshToken = request.get("refreshToken");
        String email = request.get("email");
        Map<String, String> tokens = userService.refreshToken(refreshToken, email);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Token refreshed successfully", tokens));
    }
}
