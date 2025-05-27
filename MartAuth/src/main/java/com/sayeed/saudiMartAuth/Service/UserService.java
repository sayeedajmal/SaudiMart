package com.sayeed.saudiMartAuth.Service;

import java.time.Duration;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saudimart.martAuth.Model.Users;
import com.saudimart.martAuth.Repository.UserRepository;
import com.saudimart.martAuth.Utils.JwtUtil;
import com.saudimart.martAuth.Utils.UserException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Users getUserById(String userId) throws UserException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException("User not found with ID: " + userId));
    }

    @Transactional(readOnly = true)
    public Users getUserByEmail(String email) throws UserException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found with email: " + email));
    }

    @Transactional
    public void deleteUser(String id) throws UserException {
        if (!userRepository.existsById(id)) {
            throw new UserException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    @Transactional
    public Map<String, Object> signUp(Users user) throws UserException {
        if (!isEmailAvailable(user.getEmail())) {
            throw new UserException("Email already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setIsVerified(false);
        user.setCredentialsNonExpired(true);

        Users savedUser = userRepository.save(user);

        String accessToken = jwtUtil.generateAccessToken(savedUser);
        String refreshToken = jwtUtil.generateRefreshToken(savedUser);

        redisTemplate.opsForValue().set("user:" + savedUser.getEmail(), savedUser, Duration.ofDays(2));

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("myProfile", savedUser);

        return response;
    }

    public Map<String, Object> authenticate(String email, String password) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));

        if (!user.isAccountNonExpired()) {
            throw new UserException("Your account has expired.", HttpStatus.UNAUTHORIZED);
        }
        if (!user.isAccountNonLocked()) {
            throw new UserException("Your account is locked.", HttpStatus.UNAUTHORIZED);
        }
        if (!user.isEnabled()) {
            throw new UserException("Your account is disabled.", HttpStatus.UNAUTHORIZED);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            throw new UserException("Invalid credentials");
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        redisTemplate.opsForValue().set("user:" + user.getEmail(), user, Duration.ofDays(2));

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        tokens.put("myProfile", user);

        return tokens;
    }

    public Map<String, String> refreshToken(String refreshToken, String email) throws UserException {
        if (refreshToken == null || refreshToken.isEmpty() || email == null || email.isEmpty()) {
            throw new UserException("Refresh token or email is missing");
        }

        String extractedEmail = jwtUtil.extractUserEmail(refreshToken);
        if (!extractedEmail.equals(email)) {
            throw new UserException("Token does not match the email provided");
        }

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));

        if (!jwtUtil.isRefreshValid(refreshToken, user)) {
            throw new UserException("Invalid refresh token");
        }

        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);

        return tokens;
    }

    @Transactional
    public void updateRole(String email, String role) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));

        List<String> validRoles = List.of("BUYER", "SELLER", "ADMIN");
        if (!validRoles.contains(role.toUpperCase())) {
            throw new UserException("Invalid role. Must be BUYER, SELLER, or ADMIN.");
        }

        user.setRole(role.toUpperCase());
        userRepository.save(user);
    }

    @Transactional
    public Users updateUserSelf(Users user) throws UserException {
        Users existingUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new UserException("User not found"));

        if (user.getUsername() != null && !user.getUsername().isBlank()) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPhone_number() != null && !user.getPhone_number().isBlank()) {
            existingUser.setPhone_number(user.getPhone_number());
        }
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void setUserVerificationStatus(String email, boolean isVerified) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        user.setIsVerified(isVerified);
        userRepository.save(user);
    }

    @Transactional
    public void toggleUserActivation(String email, boolean enabled) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    @Transactional
    public void lockOrUnlockUser(String email, boolean isLocked) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        user.setAccountNonLocked(!isLocked);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String redisKey = "user:" + email;
        Object cachedUser = redisTemplate.opsForValue().get(redisKey);
        Users user;

        if (cachedUser == null) {
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            redisTemplate.opsForValue().set(redisKey, user, Duration.ofDays(2));
        } else {
            user = objectMapper.convertValue(cachedUser, Users.class);
        }

        return user;
    }
}
