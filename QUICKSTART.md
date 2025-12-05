# Quick Start Guide - Sony Music Microservices

## Prerequisites Check
- [ ] Java 17 installed: `java -version`
- [ ] Maven installed: `mvn -version`
- [ ] Docker installed: `docker --version`
- [ ] Docker Compose installed: `docker-compose --version`
- [ ] Git installed: `git --version`

## Step-by-Step Setup

### 1. Run Setup Script (Windows)
```cmd
setup.bat
```

This creates the configuration repository at `%USERPROFILE%\sonymusic-config-repo`

### 2. Build All Services
```cmd
mvn clean package -DskipTests
```

**Expected**: All services build successfully with "BUILD SUCCESS"

### 3. Start Docker Infrastructure
```cmd
docker-compose up -d
```

**Expected**: 15+ containers start (databases, Kafka, Redis, Zipkin, Prometheus, Grafana, microservices)

### 4. Wait for Services to Start
```cmd
docker-compose ps
```

Wait until all services show "healthy" status (2-3 minutes)

### 5. Verify Dashboards

Open in browser:
- ✅ Eureka: http://localhost:8761 (should show 5 registered services)
- ✅ Grafana: http://localhost:3000 (login: admin/admin)
- ✅ Prometheus: http://localhost:9090 (check targets are "UP")
- ✅ Zipkin: http://localhost:9411

### 6. Test Artist Service

**Create an artist:**
```cmd
curl -X POST http://localhost:8080/api/artists -H "Content-Type: application/json" -d "{\"name\":\"Billie Eilish\",\"genre\":\"Alternative\",\"country\":\"USA\",\"monthlyListeners\":60000000,\"verified\":true}"
```

**Get all artists:**
```cmd
curl http://localhost:8080/api/artists
```

**Search artists:**
```cmd
curl "http://localhost:8080/api/artists/search?q=Taylor"
```

### 7. Verify Kafka Events

```cmd
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic artist-events --from-beginning
```

You should see JSON events for artist operations.

### 8. Check Distributed Tracing

1. Make a few API calls
2. Open Zipkin: http://localhost:9411
3. Click "Run Query"
4. Click on a trace to see the request flow

## Troubleshooting

### Services not starting
```cmd
docker-compose logs -f [service-name]
```

### Rebuild a service
```cmd
cd artist-service
mvn clean package -DskipTests
docker-compose up -d --build artist-service
```

### Reset everything
```cmd
docker-compose down -v
docker-compose up -d
```

## Interview Demo Flow

1. **Show Architecture**: Open README.md and explain the diagram
2. **Show Eureka**: http://localhost:8761 - All services registered
3. **Create Artist**: Use curl or Postman to create an artist
4. **Show Kafka Event**: Demonstrate event in Kafka console
5. **Show Tracing**: Open Zipkin and show the trace
6. **Show Metrics**: Open Grafana and show JVM metrics
7. **Test Circuit Breaker**: Stop artist-service and show fallback
8. **Show Code**: Walk through Artist Service layers

## Key Files to Show in Interview

1. `docker-compose.yml` - Complete infrastructure
2. `artist-service/src/main/java/com/sonymusic/artist/`
   - `controller/ArtistController.java` - REST endpoints
   - `service/ArtistService.java` - Business logic + Kafka
   - `entity/Artist.java` - JPA entity
3. `api-gateway/src/main/resources/application.yml` - Routes and circuit breakers
4. `monitoring/prometheus/prometheus.yml` - Metrics scraping

## Talking Points

- **Microservices**: Explain service boundaries and independence
- **Event-Driven**: Kafka for async communication
- **Resilience**: Circuit breaker demo when service is down
- **Observability**: Zipkin traces, Prometheus metrics
- **Docker**: Multi-stage builds, health checks
- **Database**: Flyway migrations, database-per-service

## Next Steps

- Add more business services (Streaming, Playlist, Notification)
- Implement Keycloak for OAuth2 authentication
- Add integration tests with Testcontainers
- Deploy to Kubernetes
- Add API documentation with Swagger/OpenAPI
