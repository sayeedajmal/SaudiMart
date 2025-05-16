package com.sayeed.saudiMartAuth.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.sayeed.saudiMartAuth.Model.Users;
import com.sayeed.saudiMartAuth.Repository.UserRepository;
import com.sayeed.saudiMartAuth.Utils.JwtUtil;
import com.sayeed.saudiMartAuth.Utils.UserException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserByEmail(String email) throws UserException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("Users not found"));
    }

    public Users getUserById(String id) throws UserException {
        return userRepository.findByUserId(id).orElseThrow(() -> new UserException("Users not found"));
    }

    public void deleteUser(String id) throws UserException {
        userRepository.deleteById(id);
    }

    public boolean isEmailAvailable(String email) {
        Optional<Users> existingUser = userRepository.findByEmail(email);
        return !existingUser.isPresent();
    }

    public Map<String, Object> signUp(Users user) throws UserException {

        if (!isEmailAvailable(user.getUsername())) {
            throw new UserException("Email already taken");
        }
        user.setAuthorities(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);

        Users myProfile = userRepository.save(user);
        String accessToken = jwtUtil.generateAccessToken(myProfile);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        myProfile.setPassword(null);
        redisTemplate.opsForValue().set("user:" + myProfile.getEmail(), myProfile, Duration.ofDays(2));

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        tokens.put("myProfile", myProfile);

        return tokens;
    }

    public Map<String, Object> authenticate(String email, String password) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("Users not found"));
        if (!user.isAccountNonExpired()) {
            throw new UserException("Your account has expired. Visit the Offical Support", HttpStatus.UNAUTHORIZED);
        }
        if (!user.isAccountNonLocked()) {
            throw new UserException("Your account is locked. Visit the Offical Support", HttpStatus.UNAUTHORIZED);
        }
        if (!user.isEnabled()) {
            throw new UserException("Your account is disabled. Visit the Offical Support", HttpStatus.UNAUTHORIZED);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    email,
                    password));
        } catch (AuthenticationException e) {
            throw new UserException(e.getMessage());
        }
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        user.setPassword(null);
        redisTemplate.opsForValue().set("user:" + user.getEmail(), user, Duration.ofDays(2));

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        tokens.put("myProfile", user);
        return tokens;
    }

    public Map<String, String> refreshToken(String refreshToken, String Email) throws UserException {
        if (refreshToken == null || refreshToken.isEmpty() || Email.isEmpty()) {
            throw new UserException("Refresh token or Email is missing or malformed");
        }

        String email = jwtUtil.extractUserEmail(refreshToken);
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("Users not found"));

        if (!jwtUtil.isRefreshValid(refreshToken, user)) {
            throw new UserException("Invalid refresh token");
        }

        // Generate new tokens
        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        // Return tokens as JSON response
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);

        return tokens;
    }

    /**
     * Used by Admin to change a user's role (BUYER, SELLER, ADMIN)
     */
    @Transactional
    public void updateRole(String email, String role) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));

        if (role.equals("BUYER") || role.equals("SELLER") || role.equals("ADMIN")) {
            user.setRole(role);
            userRepository.save(user);
        } else {
            throw new UserException("Invalid role");
        }
    }

    /**
     * Used by the logged-in user to update their own profile details.
     */
    @Transactional
    public Users updateUserSelf(Users user) throws UserException {
        Users existingUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new UserException("User not found"));

        if (user.getName() != null && !user.getName().isEmpty()) {
            existingUser.setName(user.getName());
        }

        if (user.getPhone_number() != null && !user.getPhone_number().isEmpty()) {
            existingUser.setPhone_number(user.getPhone_number());
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    /**
     * Used by Admin to verify or unverify a user manually.
     */
    @Transactional
    public void setUserVerificationStatus(String email, boolean isVerified) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        user.setIsVerified(isVerified);
        userRepository.save(user);
    }

    /**
     * 🔒 Used by Admin to disable or enable a user account.
     */
    @Transactional
    public void toggleUserActivation(String email, boolean enabled) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    /**
     * 🔒 Used by Admin to lock or unlock a user account.
     */
    @Transactional
    public void lockOrUnlockUser(String email, boolean isLocked) throws UserException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        user.setAccountNonLocked(!isLocked);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First check Redis
        String redisKey = "user:" + email;
        Users user = (Users) redisTemplate.opsForValue().get(redisKey);

        if (user != null) {
            return user;
        }

        // If not found in cache, get from DB
        user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Users not found"));

        // Save to Redis for future requests
        user.setPassword(null);
        redisTemplate.opsForValue().set(redisKey, user, Duration.ofDays(2));
        return user;
    }

}
