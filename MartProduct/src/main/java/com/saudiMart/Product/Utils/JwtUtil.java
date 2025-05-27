package com.saudiMart.Product.Utils;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * JwtUtil is a utility class for handling JWT (JSON Web Token) operations.
 * This class provides methods to generate, validate, and extract information
 * from JWTs.
 * It is used for authentication and authorization purposes within the
 * application.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.tokenValidityInMilliSeconds}")
    private long accessTokenExpire;

    @Value("${jwt.tokenRefreshInMilliSeconds}")
    private long refreshTokenExpire;

    /**
     * Extracts the user email (subject) from the given JWT.
     *
     * @param token the JWT from which the user email is to be extracted.
     * @return the extracted user email.
     * @throws UserException if the token is invalid or has expired.
     */
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the user roles from the given JWT.
     * Assumes roles are stored in the 'authorities' claim.
     *
     * @param token the JWT from which the user roles are to be extracted.
     * @return the extracted user roles as a String, or null if not present or token
     *         is invalid.
     */
    public String extractUserRoles(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("authorities", String.class); // Assuming roles are stored as a String or can be converted
        } catch (RuntimeException e) {
            // Handle exception internally, return null for invalid or missing claim
            return null;
        }
    }

    /**
     * Extracts the user id (id) from the given JWT.
     *
     * @param token the JWT from which the user id is to be extracted.
     * @return the extracted user id.
     * @throws UserException if the token is invalid or has expired.
     */
    public String extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("id", String.class);
    }

    /**
     * Validates the given JWT against the provided user details.
     *
     * @param token the JWT to validate.
     * @param user  the user details to validate against.
     * @return true if the JWT is valid, false otherwise.
     * @throws UserException
     */
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (RuntimeException e) {
            return false; // Token is invalid or expired
        }
    }

    /**
     * Checks if the given JWT has expired.
     *
     * @param token the JWT to check.
     * @return true if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (JwtException e) {

        }
        return false;
    }

    /**
     * Extracts the expiration date from the given JWT.
     *
     * @param token the JWT from which the expiration date is to be extracted.
     * @return the extracted expiration date.
     * @throws UserException if the token is invalid or has expired.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the given JWT.
     *
     * @param token    the JWT from which the claim is to be extracted.
     * @param resolver the function to extract the specific claim.
     * @param <T>      the type of the claim.
     * @return the extracted claim.
     * @throws UserException if the token is invalid or has expired.
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT.
     *
     * @param token the JWT from which the claims are to be extracted.
     * @return the extracted claims.
     * @throws UserException if the token is invalid or has expired.
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSigninKey())
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT expired", e); // Using RuntimeException
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT", e); // Using RuntimeException
        }
    }

    /**
     * Provides the signing key used for JWT operations.
     *
     * @return the signing key.
     */
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}