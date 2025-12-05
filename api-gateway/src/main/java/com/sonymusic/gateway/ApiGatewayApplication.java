package com.sonymusic.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway - Entry Point for All Client Requests
 * 
 * This service acts as a single entry point for all microservices.
 * It handles routing, authentication, rate limiting, and load balancing.
 * 
 * Key Features:
 * - Dynamic Routing: Routes requests to appropriate microservices
 * - Load Balancing: Distributes requests across service instances
 * - Authentication: OAuth2/OIDC integration with Keycloak
 * - Rate Limiting: Prevents API abuse using Redis
 * - Circuit Breaker: Resilience4j integration for fault tolerance
 * - Request/Response Transformation: Modify headers, add correlation IDs
 * - Distributed Tracing: Zipkin integration for request tracking
 * 
 * Interview Talking Points:
 * - API Gateway pattern provides a single entry point
 * - Reduces client complexity by hiding microservices architecture
 * - Enables cross-cutting concerns (auth, logging, monitoring)
 * - Spring Cloud Gateway is reactive (built on Project Reactor)
 * - Supports predicates and filters for flexible routing
 * - Can implement API versioning and request transformation
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
