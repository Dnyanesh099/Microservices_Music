package com.sonymusic.artist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Artist Service - Manages Artist Information
 * 
 * This service handles all artist-related operations including CRUD operations,
 * artist metadata management, and event publishing.
 * 
 * Key Features:
 * - RESTful API for artist management
 * - PostgreSQL database with Flyway migrations
 * - Event-driven architecture with Kafka
 * - Circuit breaker for external API calls
 * - Distributed tracing with Zipkin
 * - Metrics export to Prometheus
 * 
 * Interview Talking Points:
 * - Database-per-service pattern for data isolation
 * - Event sourcing for maintaining data consistency across services
 * - Flyway for version-controlled database schema management
 * - Bean validation for input validation
 * - MapStruct for DTO mapping (compile-time safety)
 * - Testcontainers for integration testing with real databases
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
public class ArtistServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtistServiceApplication.class, args);
    }
}
