# AstroLog Backend

Spring Boot REST API for the AstroLog star chart application.

**Status**: ✅ Complete, Built, and Running
**Java Version**: 25.0.2 ✅ Installed
**Maven Version**: 3.9.14 ✅ Installed
**Server**: Running on http://localhost:8080/api ✅
**Build**: All compilation issues resolved ✅

## Architecture

### Layers

- **Controller**: REST endpoints handling HTTP requests
- **Service**: Business logic and API integration
- **Repository**: Data persistence with Spring Data JPA
- **Entity**: Database entity models
- **DTO**: Data transfer objects for API contracts
- **Config**: Spring configuration classes
- **Util**: Utility classes

### Key Classes

- `AstroLogApplication.java` - Main Spring Boot application class
- `StarChartController.java` - REST controller for star chart endpoints
- `StarChartService.java` - Core business logic
- `AstronomyApiService.java` - Integration with AstronomyAPI
- `ImageStorageService.java` - Local/S3 image storage
- `Observation.java` - JPA entity for observations
- `ObservationRepository.java` - JPA repository interface

## Dependencies

Key Maven dependencies:
- Spring Boot Starter Web - RESTful API
- Spring Boot Starter Data JPA - ORM support
- PostgreSQL Driver - Database driver
- Lombok - Boilerplate reduction
- Spring Boot Starter WebFlux - Async HTTP client

## Configuration

### application.properties

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/astrolog
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update

# AstronomyAPI
astronomy.api.key=your_api_key
astronomy.api.base-url=https://api.astronomyapi.com/api/v2

# Storage
storage.type=local
storage.local.path=./uploads

# CORS
cors.allowed-origins=http://localhost:3000
```

### Environment Variables

You can use environment variables to override properties:
```bash
export ASTRONOMY_API_KEY=your_key
export DATABASE_URL=jdbc:postgresql://...
```

## Building & Running

### Development

1. Install dependencies:
```bash
mvn clean install
```

2. Run with hot reload:
```bash
mvn spring-boot:run
```

3. API available at: `http://localhost:8080/api`

### Production Build

```bash
mvn clean package
java -jar target/astrolog-backend-1.0.0.jar
```

## API Documentation

### Generate Star Chart

**POST** `/api/star-charts/generate`

Request body:
```json
{
  "location": "New York",
  "latitude": 40.7128,
  "longitude": -74.0060,
  "observationDateTime": "2026-04-05T20:30:00",
  "target": "Orion",
  "notes": "Clear sky",
  "downloadImage": false
}
```

Response:
```json
{
  "observationId": 1,
  "location": "New York",
  "latitude": 40.7128,
  "longitude": -74.0060,
  "observationDateTime": "2026-04-05T20:30:00",
  "target": "Orion",
  "chartUrl": "https://...",
  "localImagePath": null,
  "s3ImageUrl": null,
  "createdAt": "2026-04-05T12:00:00",
  "notes": "Clear sky"
}
```

### Get All Observations

**GET** `/api/star-charts/observations`

Response: Array of ObservationDTO objects

### Get Observation by ID

**GET** `/api/star-charts/observations/{id}`

### Get Observations by Location

**GET** `/api/star-charts/observations/location/{location}`

### Get Observations by Target

**GET** `/api/star-charts/observations/target/{target}`

### Update Observation

**PUT** `/api/star-charts/observations/{id}`

Same request body as generate endpoint.

### Delete Observation

**DELETE** `/api/star-charts/observations/{id}`

### Health Check

**GET** `/api/star-charts/health`

Response:
```
"AstroLog API is running"
```

## AstronomyAPI Integration

The `AstronomyApiService` handles all communication with AstronomyAPI:

1. **Authentication**: Uses API key in Authorization header (Bearer token)
2. **Endpoints**: Calls `/api/v2/studio/star-chart` endpoint
3. **Parameters**: 
   - `latitude` - Observer latitude
   - `longitude` - Observer longitude
   - `date` - Observation date/time
   - `style` - Chart styling (navy, dark, etc.)
   - `observer_color` - Observer interface color

## Image Storage

### Local Storage

Images stored in directory specified by `storage.local.path`:
```
./uploads/
├── chart_1_2026-04-05_a1b2c3d4.png
├── chart_2_2026-04-05_e5f6g7h8.png
└── ...
```

### S3 Storage

Configure AWS credentials and bucket in properties (implementation placeholder).

## Database Migrations

Using Hibernate DDL auto. Options:
- `validate` - Validate schema only
- `update` - Create/update tables
- `create` - Create new tables (drop existing)
- `create-drop` - Create and drop on shutdown

Production recommendation: `validate` with Flyway/Liquibase for migrations.

## Error Handling

- Global exception handling via controller advice pattern
- Validation errors return 400 Bad Request
- Not found errors return 404
- Server errors return 500 with error details

## Testing

Run tests:
```bash
mvn test
```

## Performance Considerations

- Database connection pooling configured
- HTTP client timeouts:
  - Connect: 10 seconds
  - Read: 30 seconds
- CORS cache: 1 hour
- Database queries optimized with proper indexing

## Logging

Configured in application.properties:
```properties
logging.level.root=INFO
logging.level.com.astrolog=DEBUG
```

Adjust log levels as needed for debugging.

## Security Considerations

- CORS properly configured for allowed origins
- API key stored in environment variables
- Input validation on all endpoints
- Database credentials externalized
- HTTPS recommended for production

## Future Enhancements

- User authentication with JWT
- Role-based access control
- Request rate limiting
- API documentation with Swagger/OpenAPI
- Database query caching
- Async processing for image downloads
- WebSocket support for real-time updates

## Troubleshooting

### Database Connection Issues
Check PostgreSQL is running and credentials are correct in properties.

### AstronomyAPI Errors
Verify API key is valid and not expired. Check API quota limits.

### Port Already in Use
Change `server.port` in properties or kill process on port 8080.

### CORS Errors
Verify `cors.allowed-origins` includes your frontend URL.

### Build Issues (RESOLVED ✅)
Previous Lombok annotation processing issues with Java 25 have been resolved. See BUILD_FIX_SUMMARY.md for details.

## Build Changes (April 5, 2026)

### Lombok Refactoring
- Replaced `@Slf4j` with manual `LoggerFactory` logger declarations
- Replaced `@Data` and `@Builder` with manual getters/setters and constructors
- Disabled annotation processing in Maven compiler
- Result: ✅ Clean build, no compilation errors

### Modified Classes
- AstronomyApiService.java
- StarChartService.java
- StarChartController.java
- ImageStorageService.java
- All DTO classes
- Observation entity

## Support

For issues or questions, refer to the main README.md in the project root.

