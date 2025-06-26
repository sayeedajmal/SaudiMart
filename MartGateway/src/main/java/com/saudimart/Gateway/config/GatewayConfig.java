package com.saudimart.Gateway.config;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public ErrorWebExceptionHandler gatewayErrorWebExceptionHandler() {
        return new GatewayErrorWebExceptionHandler();
    }
}