#!/bin/bash

# Sony Music Microservices - Setup Script
# This script sets up the local configuration repository for Config Server

echo "========================================="
echo "Sony Music Microservices - Setup"
echo "========================================="
echo ""

# Create config repository directory
CONFIG_REPO_DIR="$HOME/sonymusic-config-repo"

if [ -d "$CONFIG_REPO_DIR" ]; then
    echo "✓ Config repository already exists at $CONFIG_REPO_DIR"
else
    echo "Creating config repository at $CONFIG_REPO_DIR..."
    mkdir -p "$CONFIG_REPO_DIR"
    cd "$CONFIG_REPO_DIR"
    
    # Initialize git repository
    git init
    
    # Create application.yml (common config)
    cat > application.yml << 'EOF'
# Common configuration for all services
spring:
  jpa:
    show-sql: false
    properties:
      hibernate:
        format_sql: true
  kafka:
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true
  tracing:
    sampling:
      probability: 1.0

logging:
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%X{traceId}/%X{spanId}] [%thread] %-5level %logger{36} - %msg%n"
EOF
    
    # Commit the changes
    git add .
    git commit -m "Initial configuration"
    
    echo "✓ Config repository created and initialized"
fi

echo ""
echo "========================================="
echo "Setup Complete!"
echo "========================================="
echo ""
echo "Next steps:"
echo "1. Build the project: mvn clean package -DskipTests"
echo "2. Start services: docker-compose up -d"
echo "3. Check status: docker-compose ps"
echo "4. View logs: docker-compose logs -f"
echo ""
echo "Dashboards:"
echo "- Eureka: http://localhost:8761"
echo "- API Gateway: http://localhost:8080"
echo "- Grafana: http://localhost:3000 (admin/admin)"
echo "- Prometheus: http://localhost:9090"
echo "- Zipkin: http://localhost:9411"
echo ""
