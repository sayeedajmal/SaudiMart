package com.saudimart.Gateway.Security.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.saudimart.Gateway.Utils.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @SuppressWarnings("null")
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        var response = exchange.getResponse();
        String path = request.getPath().value();
        HttpMethod method = request.getMethod();

        if (path.matches("^/(authen|actuator|users)(/.*)?$") || (HttpMethod.GET.equals(method) || HttpMethod.OPTIONS.equals(method)) &&
                path.matches("^/(categories|pricetiers|productimages|productspecifications|productvariants|products)(/.*)?$")) {
                System.out.println("ITS FROM THE GATEWAY OPENEND POINT HERES THE PATH: "+path);
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // Build new request with user details for downstream services
        String userId = jwtUtil.extractId(token);

        // Always get roles as a list (handles both String and JSON list cases)
        List<String> rolesList = jwtUtil.extractRolesList(token);

        // Join into CSV string for headers if needed
        String rolesCsv = String.join(",", rolesList);

        // Modify request headers
        var modifiedRequest = request.mutate()
                .header("X-User-Id", userId)
                .header("X-User-Roles", rolesCsv)
                .build();

        // Convert roles to GrantedAuthority list
        List<GrantedAuthority> authorities = rolesList.stream()
                .filter(role -> !role.isBlank())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Set authentication in the security context
        var auth = new UsernamePasswordAuthenticationToken(userId, null, authorities);
        var ctx = new SecurityContextImpl(auth);
        System.out.println("ITS FROM THE GATEWAY WITH AUTHORIZATION HERE IS THE PATH: "+path);
        return chain.filter(exchange.mutate().request(modifiedRequest).build())
                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(ctx)));
    }
}
