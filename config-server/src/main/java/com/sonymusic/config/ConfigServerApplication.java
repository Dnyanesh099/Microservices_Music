package com.sonymusic.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Config Server - Centralized Configuration Management
 * 
 * This service provides centralized configuration management for all microservices.
 * It uses a Git repository to store configuration files and serves them to clients.
 * 
 * Key Features:
 * - Centralized Configuration: All service configs in one place
 * - Environment-Specific: Different configs for dev, staging, prod
 * - Dynamic Refresh: Update configs without restarting services
 * - Version Control: Git-backed configuration history
 * - Encryption: Support for encrypted properties
 * 
 * Interview Talking Points:
 * - Externalized configuration is a 12-factor app principle
 * - Enables configuration changes without rebuilding applications
 * - Supports multiple backends (Git, SVN, filesystem, Vault)
 * - Can encrypt sensitive data like passwords and API keys
 * - Integrates with Spring Cloud Bus for dynamic refresh across instances
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
