package com.saudimart.Gateway.Security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.saudimart.Gateway.Utils.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (request.getURI().getPath().startsWith("/auth/")) {
            return chain.filter(exchange);
        }

        final String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        try {
            String jwt = authHeader.substring(7);

            if (!jwtUtil.validateToken(jwt)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            String id = jwtUtil.extractId(jwt);
            String userRoles = jwtUtil.extractUserRoles(jwt);

            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", id)
                    .header("X-User-Roles", userRoles)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        } catch (RuntimeException e) {
            System.err.println("Error processing JWT: " + e.getMessage());
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
