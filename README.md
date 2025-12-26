# Renault Garage Management Microservice

A comprehensive Spring Boot microservice for managing Renault garages, vehicles, and accessories with event-driven architecture, Swagger UI documentation, and complete test coverage.

### Core Functionality
- **Garage Management**: Full CRUD operations with pagination and sorting
- **Vehicle Management**: Add, update, delete vehicles with 50-vehicle quota per garage
- **Accessory Management**: Complete accessory lifecycle management for vehicles
- **Search Capabilities**: Find vehicles by model across multiple garages

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **H2 Database** 
- **Lombok** 
- **Springdoc OpenAPI** (Swagger UI)
- **Maven** 
- **JUnit 5 & Mockito** 

## Quick Start

### 1. Clone and Build

```
cd Renault_Garages_Microservice
mvn clean install
```

### 2. Run Tests

```
mvn test
```

### 3. Run Application 

```
mvn spring-boot:run
```

The application will start on http://localhost:8081

## API Endpoints

### Swagger UI
Access interactive API documentation:
```
http://localhost:8081/swagger-ui.html
```


### Example API Calls

#### Create a Garage

```
curl -X POST http://localhost:8080/api/garages \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Renault 0",
    "address": "000 Address, Paris",
    "telephone": "+00000006790",
    "email": "test0@hotmail.fr",
    "openingHours": "{\"MONDAY\": [{\"startTime\": \"09:00\", \"endTime\": \"08:00\"}]}"
}'
```

#### Add a Vehicle to Garage

```
curl -X POST http://localhost:8081/api/garages/1/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Renault",
    "model": "Clio",
    "manufacturingYear": 2023,
    "fuelType": "Diesel"
  }'
```

### Add an Accessory to Vehicle

```
curl -X POST http://localhost:8081/api/vehicles/1/accessories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "accessory 1",
    "description": "Accessory 1 description",
    "price": 15.99,
    "type": "Interieur"
}'
```

### Search Vehicles by Model

```
curl http://localhost:8080/api/vehicles/model/Clio
```

## Database

### H2 Console
Access the H2 database console at:
```
http://localhost:8081/h2-console
```

Connection details:
- **JDBC URL**: jdbc:h2:mem:garagedb
- **Username**: sa
- **Password**: (empty)

### Schema
The application automatically creates tables on startup:
- `garage` - Garage information
- `vehicle` - Vehicle information 
- `accessory` - Accessory information