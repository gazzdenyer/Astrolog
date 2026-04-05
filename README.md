# AstroLog - Star Chart Application

A full-stack web application for generating and logging astronomical star charts. Built with Spring Boot backend and React frontend.

**Status**: ✅ **COMPLETE, FULLY FUNCTIONAL & ON GITHUB**

**GitHub Repository**: https://github.com/gazzdenyer/Astrolog
**Running**: Backend on port 8080 | Frontend on port 3000
**Database**: H2 In-Memory

## Features

- **Star Chart Generation**: Generate accurate star charts for any location, date, and time
- **Target Selection**: Search by constellation, RA/Dec coordinates, or other astronomical targets
- **Observation Logging**: Save and manage your observation history
- **Image Storage**: Optional local or S3 storage of generated charts
- **Responsive Design**: Works on desktop and mobile devices
- **Real-time Updates**: Instant display of generated star charts

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.4
- **Language**: Java 25.0.2 ✅ (Installed)
- **Database**: PostgreSQL (with H2 option for development)
- **ORM**: Spring Data JPA / Hibernate
- **HTTP Client**: RestTemplate
- **Build Tool**: Maven 3.9.14 ✅ (Installed)

### Frontend
- **Framework**: React 18.2.0
- **Build Tool**: Vite
- **HTTP Client**: Axios
- **Routing**: React Router DOM
- **State Management**: Context API
- **Node.js**: v24.14.1 ✅ (Installed)
- **npm**: 11.11.0 ✅ (Installed)

## Project Structure

```
AstroLog/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/astrolog/backend/
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── entity/
│   │   │   │   ├── dto/
│   │   │   │   ├── repository/
│   │   │   │   └── config/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── StarChartForm.jsx
│   │   │   ├── StarChartDisplay.jsx
│   │   │   └── ObservationLog.jsx
│   │   ├── pages/
│   │   ├── services/
│   │   │   └── starChartAPI.js
│   │   ├── context/
│   │   │   └── ObservationContext.jsx
│   │   ├── styles/
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── index.css
│   ├── public/
│   ├── package.json
│   ├── vite.config.js
│   └── index.html
│
└── README.md
```

## Quick Start

### Prerequisites
✅ **All prerequisites are installed on your system:**
- Java 25.0.2 (JDK)
- Node.js v24.14.1 and npm 11.11.0
- Maven 3.9.14
- PostgreSQL 12+ (or use H2 for development)
- AstronomyAPI credentials (Application ID + Secret OR API Key from https://astronomyapi.com/)

### Backend Setup

1. Navigate to backend directory:
```bash
cd backend
```

2. Configure database and API credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/astrolog
spring.datasource.username=postgres
spring.datasource.password=your_password

# Option 1: Application ID + Secret (Recommended)
astronomy.api.id=your_application_id
astronomy.api.secret=your_application_secret

# Option 2: API Key (Alternative)
# astronomy.api.key=your_api_key
```

Or use H2 for development (see application.properties comments).

3. Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

Backend runs on `http://localhost:8080/api`

### Frontend Setup

1. Navigate to frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start development server:
```bash
npm run dev
```

Frontend runs on `http://localhost:3000`

## API Endpoints

### Star Charts
- `POST /api/star-charts/generate` - Generate a new star chart
- `GET /api/star-charts/health` - Health check

### Observations
- `GET /api/star-charts/observations` - Get all observations
- `GET /api/star-charts/observations/{id}` - Get observation by ID
- `GET /api/star-charts/observations/location/{location}` - Search by location
- `GET /api/star-charts/observations/target/{target}` - Search by target
- `PUT /api/star-charts/observations/{id}` - Update observation
- `DELETE /api/star-charts/observations/{id}` - Delete observation

## Configuration

### Environment Variables

Backend (`application.properties`):
- `ASTRONOMY_API_KEY` - Your AstronomyAPI key
- `spring.datasource.url` - Database URL
- `spring.datasource.username` - Database username
- `spring.datasource.password` - Database password

Frontend (`.env`):
```
VITE_API_BASE_URL=http://localhost:8080/api
```

## Database Schema

### Observations Table
- `id` (Long) - Primary key
- `location` (String) - Location name
- `latitude` (Double) - Observer latitude
- `longitude` (Double) - Observer longitude
- `observation_datetime` (LocalDateTime) - Date/time of observation
- `target` (String) - Target (constellation, RA/Dec, etc.)
- `chart_url` (String) - URL of generated chart
- `local_image_path` (String) - Local storage path
- `s3_image_url` (String) - S3 storage URL
- `notes` (String) - Optional notes
- `created_at` (LocalDateTime) - Creation timestamp
- `updated_at` (LocalDateTime) - Last update timestamp

## Features in Detail

### Star Chart Generation
Users can generate accurate star charts by providing:
- Location (name and coordinates)
- Date and time of observation
- Target (constellation, RA/Dec coordinates, etc.)

The backend calls the AstronomyAPI with these parameters and returns a generated star chart image.

### Observation Logging
All generated star charts are automatically saved to the database with:
- Full observation details
- Chart URL reference
- Optional notes and observations
- Timestamp of creation

### Image Storage
Two storage options:
1. **Local Storage**: Charts saved to local filesystem
2. **S3 Storage**: Charts uploaded to AWS S3 (requires configuration)

Configure in `application.properties`:
```properties
storage.type=local
storage.local.path=./uploads
```

## Development

### Backend Development
- Spring Boot application with hot reload support
- Detailed logging enabled for debugging
- Exception handling and validation
- CORS configured for frontend

### Frontend Development
- Fast refresh with Vite
- React DevTools support
- Axios interceptors for error handling
- Context API for state management

## Building for Production

### Backend
```bash
cd backend
mvn clean package
java -jar target/astrolog-backend-1.0.0.jar
```

### Frontend
```bash
cd frontend
npm run build
# Serve the dist folder
```

## Deployment

See individual backend and frontend README files for detailed deployment instructions.

## Troubleshooting

### Common Issues

1. **CORS Errors**: Ensure frontend and backend CORS are properly configured
2. **Database Connection**: Check PostgreSQL is running and credentials are correct
3. **API Key**: Verify AstronomyAPI key is valid and has quota remaining
4. **Port Conflicts**: Ensure ports 8080 (backend) and 3000 (frontend) are available

## Future Enhancements

- User authentication and multi-user support
- Google Maps integration for location selection
- Advanced filtering and search
- Export observations to PDF
- Star catalog search
- Real-time sky position tracking
- Mobile app with PWA support

## License

This project is licensed under the MIT License.

## Support

For issues, questions, or contributions, please open an issue on the project repository.

## External APIs

- **AstronomyAPI**: https://astronomyapi.com/ - Star chart generation and astronomical data

## Contributors

Created with ❤️ for astronomy enthusiasts

