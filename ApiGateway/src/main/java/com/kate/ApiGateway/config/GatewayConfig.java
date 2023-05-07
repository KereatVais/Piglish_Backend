package com.kate.ApiGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("dictionary-microservice", r -> r.path("/api/words/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://dictionary-microservice"))
                .route("testing-microservice", r -> r.path("/api/testing/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://testing-microservice"))
                .route("auth-microservice", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-microservice"))
                .build();
    }
}
