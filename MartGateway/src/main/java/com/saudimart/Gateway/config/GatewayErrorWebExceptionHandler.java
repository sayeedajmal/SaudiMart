package com.saudimart.Gateway.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Order(-1)
public class GatewayErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @SuppressWarnings("null")
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        HttpStatus status;
        String errorMessage;

        if (ex instanceof AuthenticationException || ex instanceof AccessDeniedException) {
            status = HttpStatus.UNAUTHORIZED;
            errorMessage = "Authentication or Access Denied: " + ex.getMessage();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorMessage = "Internal Server Error: " + ex.getMessage();
        }

        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        DataBufferFactory bufferFactory = response.bufferFactory();
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("message", errorMessage);
        // You might add more details here depending on your needs

        byte[] bytes = toJson(errorAttributes).getBytes(); // Implement toJson helper or use a library

        return response.writeWith(Mono.just(bufferFactory.wrap(bytes)));
    }

    // Simple helper to convert Map to a JSON string.
    // In a real application, consider using Jackson ObjectMapper.
    private String toJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        map.forEach((key, value) -> {
            json.append("\"").append(key).append("\":");
            if (value instanceof String) {
                json.append("\"").append(value).append("\"");
            } else {
                json.append(value);
            }
            json.append(",");
        });
        // Remove the last comma if present
        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");
        return json.toString();
    }
}