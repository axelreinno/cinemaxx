# Nostra Cinemaxx

A movie booking system built with Spring Boot that provides features for movie ticket booking, showtime management, and cinema administration.

## Requirements

### Development Tools
- Java 17 or higher
- Maven 3.8+
- Docker and Docker Compose
- PostgreSQL 15 (via Docker)
- MinIO Object Storage (via Docker)

### Environment Setup
- SMTP Server (Mailtrap or similar for email notifications)
- MinIO credentials
- PostgreSQL database

## Running with Docker Compose

The application uses Docker Compose to run its required services (PostgreSQL and MinIO).

1. Start the services using Docker Compose:
```bash
docker compose up -d
```

This will start:
- PostgreSQL database on port 5432 and init sql data
- MinIO server on port 9000 (API) and 9001 (Console)

2. Default credentials:
   - PostgreSQL:
     - Database: cinemaxx
     - Username: admin
     - Password: admin
   - MinIO:
     - Console URL: http://localhost:9001
     - Username: admin
     - Password: admin123

## Running the Application

### Method 1: Using Maven

1. Make sure Docker Compose services are running

2. Build the application:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

### Method 2: Using JAR file

1. Make sure Docker Compose services are running

2. Build the JAR file:
```bash
mvn clean package
```

3. Run the JAR file:
```bash
java -jar target/cinemaxx-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, you can access:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Docs: http://localhost:8080/v3/api-docs

## Features

- User Authentication and Authorization
- Movie Management
- Cinema and Showtime Management
- Seat Selection and Booking
- Email Notifications
- File Storage using MinIO
- JWT-based Authentication

## Project Structure

```
src/main/java/com/academy/cinemaxx/
├── audits/          # Auditing configurations
├── configs/         # Application configurations
├── controllers/     # REST API controllers
├── dtos/           # Data Transfer Objects
├── entities/       # JPA entities
├── enums/          # Enumerations
├── exceptions/     # Custom exceptions
├── repositories/   # JPA repositories
├── security/       # Security configurations
├── services/       # Business logic
└── utils/          # Utility classes
``` 