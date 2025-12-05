package com.sonymusic.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * Gateway Configuration
 * 
 * Configures rate limiting, CORS, and other gateway behaviors.
 */
@Configuration
public class GatewayConfig {

    /**
     * Rate limiting key resolver based on IP address
     * In production, you might want to use user ID or API key
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(
            exchange.getRequest()
                .getRemoteAddress()
                .getAddress()
                .getHostAddress()
        );
    }
}
