package com.sonymusic.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Discovery Server - Eureka Server for Service Registration and Discovery
 * 
 * This service acts as a service registry where all microservices register themselves.
 * It enables client-side load balancing and service discovery.
 * 
 * Key Features:
 * - Service Registration: All microservices register with this server
 * - Service Discovery: Clients can discover other services
 * - Health Monitoring: Tracks health status of registered services
 * - Load Balancing: Enables client-side load balancing
 * 
 * Interview Talking Points:
 * - Eureka is part of Netflix OSS stack
 * - Provides high availability through peer-to-peer replication
 * - Self-preservation mode prevents mass de-registration during network issues
 * - RESTful API for service registration and discovery
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
