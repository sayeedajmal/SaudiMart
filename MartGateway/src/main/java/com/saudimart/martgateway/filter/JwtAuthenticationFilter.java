package com.saudimart.martgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.saudimart.martgateway.Utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    // Inject your JwtUtil here
    private final JwtUtil jwtUtil;

    // Constructor with JwtUtil injection
 public JwtAuthenticationFilter(JwtUtil jwtUtil) {  this.jwtUtil = jwtUtil;  }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // Exclude paths that don't require authentication (e.g., /auth/**)
        // You might want a more sophisticated way to handle unsecured paths later
        if (request.getURI().getPath().startsWith("/auth/")) {
            return chain.filter(exchange);
        }

        final String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If no JWT is present, deny access to protected routes
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        try {
            String jwt = authHeader.substring(7);
            
            // 1. Uncomment and use `jwtUtil.validateToken(jwt)`
            if (!jwtUtil.validateToken(jwt)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            // 2. Uncomment and use `jwtUtil.extractUserId(jwt)`
            String userId = jwtUtil.extractUserId(jwt);
            // 3. Uncomment and use `jwtUtil.extractUserRoles(jwt)`
            String userRoles = jwtUtil.extractUserRoles(jwt);

            // Add user info to request headers for downstream services
            // 4. Uncomment the lines that add the X-User-Id and X-User-Roles headers
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", userId) 
                    .header("X-User-Roles", userRoles) 
                    .build();

            // Proceed with the filter chain with the modified request
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        } catch (RuntimeException e) { // 5. Add appropriate exception handling for RuntimeException
            System.err.println("Error processing JWT: " + e.getMessage());
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        // Set a high priority to ensure this filter runs early in the chain
        return -1;
    }
}