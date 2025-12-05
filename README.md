# Sony Music Microservices Platform

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A production-grade microservices architecture for Sony Music built with Java, Spring Boot, and modern cloud-native technologies. This project demonstrates enterprise-level best practices suitable for technical interviews and real-world applications.

## 🏗️ Architecture Overview

```
┌─────────────┐
│   Clients   │
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────────────────────────┐
│                    API Gateway (8080)                    │
│         ┌──────────────┬──────────────┬────────┐        │
│         │ Rate Limiting│Circuit Breaker│ Auth   │        │
│         └──────────────┴──────────────┴────────┘        │
└─────────────────────────────────────────────────────────┘
       │
       ├──────────┬──────────┬──────────┬──────────┐
       ▼          ▼          ▼          ▼          ▼
┌──────────┐ ┌────────┐ ┌──────────┐ ┌─────────┐ ┌────────────┐
│ Artist   │ │ Album  │ │Streaming │ │Playlist │ │Notification│
│ Service  │ │Service │ │ Service  │ │ Service │ │  Service   │
│  :8081   │ │ :8082  │ │  :8083   │ │  :8084  │ │   :8085    │
└────┬─────┘ └───┬────┘ └────┬─────┘ └────┬────┘ └─────┬──────┘
     │           │           │            │            │
     ▼           ▼           ▼            ▼            │
┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐      │
│Artist DB│ │Album DB │ │Stream DB│ │List DB  │      │
└─────────┘ └─────────┘ └─────────┘ └─────────┘      │
                                                       │
     ┌─────────────────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────────────────────────┐
│                    Kafka Event Bus                       │
└─────────────────────────────────────────────────────────┘
```

## 🚀 Features

### Infrastructure Services
- **Discovery Server (Eureka)**: Service registration and discovery
- **Config Server**: Centralized configuration management
- **API Gateway**: Single entry point with routing, rate limiting, and circuit breakers

### Business Services
- **Artist Service**: Manage artist information and metadata
- **Album Service**: Handle album catalog and tracks
- **Streaming Service**: Track streaming analytics and play counts
- **Playlist Service**: User playlist management
- **Notification Service**: Event-driven notification system

### Technology Stack
- **Backend**: Java 17, Spring Boot 3.2, Spring Cloud 2023
- **Database**: PostgreSQL 16 (database-per-service pattern)
- **Messaging**: Apache Kafka
- **Caching**: Redis
- **Tracing**: Zipkin
- **Monitoring**: Prometheus + Grafana
- **Containerization**: Docker + Docker Compose
- **Resilience**: Resilience4j (Circuit Breaker, Retry, Rate Limiter)
- **Database Migration**: Flyway
- **Testing**: JUnit 5, Testcontainers

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.9+**
- **Docker** and **Docker Compose**
- **Git**
- At least **8GB RAM** for running the complete stack

## 🛠️ Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/Dnyanesh099/Microservices_Music.git
cd Microservices_Music
```

### 2. Build All Services

```bash
mvn clean package -DskipTests
```

### 3. Start the Complete Stack

```bash
docker-compose up -d
```

### 4. Verify Services

Wait for all services to start (approximately 2-3 minutes), then check:

```bash
docker-compose ps
```

All services should show as "healthy".

### 5. Access the Dashboards

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090
- **Zipkin**: http://localhost:9411

## 📖 API Documentation

### Artist Service Endpoints

```bash
# Get all artists
GET http://localhost:8080/api/artists

# Get artist by ID
GET http://localhost:8080/api/artists/{id}

# Create new artist
POST http://localhost:8080/api/artists
Content-Type: application/json

{
  "name": "Taylor Swift",
  "genre": "Pop",
  "country": "USA",
  "biography": "American singer-songwriter",
  "monthlyListeners": 95000000,
  "verified": true
}

# Update artist
PUT http://localhost:8080/api/artists/{id}
Content-Type: application/json

{
  "name": "Taylor Swift",
  "genre": "Pop",
  "country": "USA"
}

# Delete artist
DELETE http://localhost:8080/api/artists/{id}

# Search artists
GET http://localhost:8080/api/artists/search?q=Taylor

# Get artists by genre
GET http://localhost:8080/api/artists/genre/Pop
```

### Album Service Endpoints

```bash
# Get all albums
GET http://localhost:8080/api/albums

# Get album by ID
GET http://localhost:8080/api/albums/{id}

# Create new album
POST http://localhost:8080/api/albums
Content-Type: application/json

{
  "title": "1989",
  "artistId": 1,
  "releaseDate": "2014-10-27",
  "genre": "Pop",
  "label": "Big Machine Records"
}
```

## 🔍 Testing the System

### Using cURL

```bash
# Create an artist
curl -X POST http://localhost:8080/api/artists \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Billie Eilish",
    "genre": "Alternative",
    "country": "USA",
    "monthlyListeners": 60000000,
    "verified": true
  }'

# Get all artists
curl http://localhost:8080/api/artists

# Search artists
curl "http://localhost:8080/api/artists/search?q=Billie"
```

### Verify Kafka Events

```bash
# Check Kafka topics
docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092

# Consume artist events
docker exec -it kafka kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic artist-events \
  --from-beginning
```

### Check Distributed Tracing

1. Make some API calls
2. Open Zipkin UI: http://localhost:9411
3. Click "Run Query" to see traces
4. Click on a trace to see the complete request flow across services

### Monitor Metrics

1. Open Grafana: http://localhost:3000 (admin/admin)
2. Navigate to Dashboards
3. View JVM metrics, request rates, and response times

## 🏗️ Project Structure

```
sonymusic-microservices/
├── discovery-server/          # Eureka Server
├── config-server/             # Config Server
├── api-gateway/               # API Gateway
├── artist-service/            # Artist microservice
├── album-service/             # Album microservice
├── streaming-service/         # Streaming microservice
├── playlist-service/          # Playlist microservice
├── notification-service/      # Notification microservice
├── monitoring/
│   ├── prometheus/            # Prometheus config
│   └── grafana/               # Grafana dashboards
├── docker-compose.yml         # Docker orchestration
└── pom.xml                    # Parent POM
```

## 🎯 Key Design Patterns

1. **API Gateway Pattern**: Single entry point for all client requests
2. **Service Discovery**: Dynamic service registration with Eureka
3. **Database per Service**: Each service has its own PostgreSQL database
4. **Event-Driven Architecture**: Kafka for asynchronous communication
5. **Circuit Breaker**: Resilience4j for fault tolerance
6. **Centralized Configuration**: Spring Cloud Config
7. **Distributed Tracing**: Zipkin for request tracking
8. **CQRS**: Separate read and write models where appropriate

## 🔒 Resilience Patterns

- **Circuit Breaker**: Prevents cascading failures
- **Retry**: Automatic retry for transient failures
- **Rate Limiting**: Prevents API abuse (Redis-based)
- **Timeout**: Request timeout configuration
- **Bulkhead**: Isolates resources for different operations

## 📊 Monitoring & Observability

- **Metrics**: Prometheus scrapes metrics from all services
- **Visualization**: Grafana dashboards for real-time monitoring
- **Distributed Tracing**: Zipkin tracks requests across services
- **Health Checks**: Actuator endpoints for service health
- **Logging**: Structured logging with correlation IDs

## 🧪 Testing Strategy

- **Unit Tests**: JUnit 5 for business logic
- **Integration Tests**: Testcontainers for database and Kafka
- **API Tests**: REST Assured for endpoint testing
- **Contract Tests**: Ensure service compatibility

## 🚀 Deployment

### Local Development

```bash
# Start infrastructure only
docker-compose up -d postgres-artist postgres-album kafka redis zipkin

# Run services locally
cd artist-service
mvn spring-boot:run
```

### Production Deployment

```bash
# Build all services
mvn clean package

# Deploy with Docker Compose
docker-compose up -d

# Scale a service
docker-compose up -d --scale artist-service=3
```

## 📝 Interview Talking Points

### Architecture
- Microservices architecture with clear service boundaries
- Database-per-service pattern for data isolation
- Event-driven communication with Kafka
- API Gateway for centralized routing and security

### Resilience
- Circuit breakers prevent cascading failures
- Retry mechanisms for transient errors
- Rate limiting prevents system overload
- Timeout configurations for all external calls

### Observability
- Distributed tracing with Zipkin
- Metrics collection with Prometheus
- Visualization with Grafana
- Structured logging with correlation IDs

### Data Management
- Flyway for version-controlled database migrations
- PostgreSQL for relational data
- Optimized queries with proper indexing
- Database connection pooling

### Development Practices
- Multi-stage Docker builds for optimization
- Health checks for all services
- Comprehensive error handling
- Bean validation for input validation
- MapStruct for type-safe DTO mapping

## 🛠️ Troubleshooting

### Services not starting

```bash
# Check logs
docker-compose logs -f [service-name]

# Restart a service
docker-compose restart [service-name]
```

### Database connection issues

```bash
# Check database health
docker exec -it postgres-artist pg_isready -U artist

# Connect to database
docker exec -it postgres-artist psql -U artist -d artistdb
```

### Kafka issues

```bash
# Check Kafka logs
docker-compose logs -f kafka

# List topics
docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092
```

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Resilience4j Documentation](https://resilience4j.readme.io/)
- [Docker Documentation](https://docs.docker.com/)

## 👨‍💻 Author

**Dnyanesh Patil**
- GitHub: [@Dnyanesh099](https://github.com/Dnyanesh099)
- LinkedIn: [Your LinkedIn Profile]

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Spring Boot and Spring Cloud teams
- Netflix OSS for Eureka
- Resilience4j team
- The open-source community

---

**Built with ❤️ for demonstrating production-grade microservices architecture**
