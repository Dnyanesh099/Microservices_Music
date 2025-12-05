@echo off
REM Sony Music Microservices - Setup Script for Windows
REM This script sets up the local configuration repository for Config Server

echo =========================================
echo Sony Music Microservices - Setup
echo =========================================
echo.

REM Create config repository directory
set CONFIG_REPO_DIR=%USERPROFILE%\sonymusic-config-repo

if exist "%CONFIG_REPO_DIR%" (
    echo Config repository already exists at %CONFIG_REPO_DIR%
) else (
    echo Creating config repository at %CONFIG_REPO_DIR%...
    mkdir "%CONFIG_REPO_DIR%"
    cd /d "%CONFIG_REPO_DIR%"
    
    REM Initialize git repository
    git init
    
    REM Create application.yml
    (
        echo # Common configuration for all services
        echo spring:
        echo   jpa:
        echo     show-sql: false
        echo   kafka:
        echo     producer:
        echo       key-serializer: org.apache.kafka.common.serialization.StringSerializer
        echo       value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
        echo.
        echo management:
        echo   endpoints:
        echo     web:
        echo       exposure:
        echo         include: health,info,metrics,prometheus
        echo   tracing:
        echo     sampling:
        echo       probability: 1.0
    ) > application.yml
    
    REM Commit the changes
    git add .
    git commit -m "Initial configuration"
    
    echo Config repository created and initialized
)

echo.
echo =========================================
echo Setup Complete!
echo =========================================
echo.
echo Next steps:
echo 1. Build the project: mvn clean package -DskipTests
echo 2. Start services: docker-compose up -d
echo 3. Check status: docker-compose ps
echo 4. View logs: docker-compose logs -f
echo.
echo Dashboards:
echo - Eureka: http://localhost:8761
echo - API Gateway: http://localhost:8080
echo - Grafana: http://localhost:3000 (admin/admin)
echo - Prometheus: http://localhost:9090
echo - Zipkin: http://localhost:9411
echo.
pause
