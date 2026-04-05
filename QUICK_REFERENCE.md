# AstroLog - Quick Reference Guide

**Status**: ✅ Complete, running, and on GitHub
**GitHub**: https://github.com/gazzdenyer/Astrolog
**Backend**: http://localhost:8080/api
**Frontend**: http://localhost:3000

## 🚀 Get Started in 5 Minutes

### ✅ Prerequisites Status
- Java 25.0.2 ✅ Installed
- Node.js v24.14.1 ✅ Installed
- npm 11.11.0 ✅ Installed
- Maven 3.9.14 ✅ Installed
- PostgreSQL 12+ (or use H2)
- AstronomyAPI key from https://astronomyapi.com/

### Quick Setup

```bash
# Terminal 1: Backend
cd backend
mvn spring-boot:run

# Terminal 2: Frontend  
cd frontend
npm install
npm run dev

# Open browser: http://localhost:3000
```

## 📁 Project Structure

```
AstroLog/
├── backend/          # Spring Boot REST API
├── frontend/         # React web application
├── README.md         # Project overview
├── SETUP.md          # Detailed setup guide
└── docker-compose.yml # Docker deployment
```

## 🛠️ Configuration

### Backend Configuration
**File:** `backend/src/main/resources/application.properties`

```properties
# Database (choose one)
spring.datasource.url=jdbc:postgresql://localhost:5432/astrolog
# OR for H2:
# spring.datasource.url=jdbc:h2:mem:testdb

# API Credentials (choose one)
# Option 1: Application ID + Secret (Recommended)
astronomy.api.id=your_application_id
astronomy.api.secret=your_application_secret

# Option 2: API Key (Alternative)
# astronomy.api.key=your_api_key

# Storage
storage.type=local
storage.local.path=./uploads
```

### Frontend Configuration
**File:** `frontend/.env` (optional)

```
VITE_API_BASE_URL=http://localhost:8080/api
```

## 🌐 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/star-charts/generate` | Generate star chart |
| GET | `/api/star-charts/observations` | Get all observations |
| GET | `/api/star-charts/observations/{id}` | Get by ID |
| GET | `/api/star-charts/observations/location/{location}` | Filter by location |
| GET | `/api/star-charts/observations/target/{target}` | Filter by target |
| PUT | `/api/star-charts/observations/{id}` | Update observation |
| DELETE | `/api/star-charts/observations/{id}` | Delete observation |
| GET | `/api/star-charts/health` | Health check |

## 📊 Database Schema

**Table: observations**

| Column | Type | Description |
|--------|------|-------------|
| id | Long | Primary key |
| location | String | Location name |
| latitude | Double | -90 to 90 |
| longitude | Double | -180 to 180 |
| observation_datetime | DateTime | Observation time |
| target | String | Constellation/RA/Dec |
| chart_url | String | Generated chart URL |
| local_image_path | String | Local storage path |
| s3_image_url | String | S3 URL |
| notes | String | Optional notes |
| created_at | DateTime | Creation timestamp |
| updated_at | DateTime | Update timestamp |

## 🎨 Frontend Components

| Component | File | Purpose |
|-----------|------|---------|
| StarChartForm | `StarChartForm.jsx` | Input form for star charts |
| StarChartDisplay | `StarChartDisplay.jsx` | Display chart & details |
| ObservationLog | `ObservationLog.jsx` | View & manage observations |
| Context | `ObservationContext.jsx` | Global state management |

## 🔧 Key Backend Classes

| Class | File | Purpose |
|-------|------|---------|
| StarChartController | `controller/` | REST endpoints |
| StarChartService | `service/` | Business logic |
| AstronomyApiService | `service/` | AstronomyAPI integration |
| ImageStorageService | `service/` | Image storage (local/S3) |
| Observation | `entity/` | Database entity |
| ObservationRepository | `repository/` | JPA repository |

## 🐳 Docker Deployment

```bash
# Build and run everything
docker-compose up

# Services:
# - PostgreSQL: localhost:5432
# - Backend: localhost:8080
# - Frontend: localhost:3000
```

## 🧪 Testing

### Backend Test
```bash
cd backend
mvn test
```

### Frontend Test
```bash
cd frontend
npm test
```

### Manual Test
1. Fill form: Location (New York), Lat (40.7128), Lon (-74.0060), DateTime (now), Target (Orion)
2. Click "Generate Star Chart"
3. Verify chart appears
4. Check observation log
5. Filter and delete observations

## 🔍 Common Endpoints

### Generate Chart
```bash
curl -X POST http://localhost:8080/api/star-charts/generate \
  -H "Content-Type: application/json" \
  -d '{
    "location": "New York",
    "latitude": 40.7128,
    "longitude": -74.0060,
    "observationDateTime": "2026-04-05T20:30:00",
    "target": "Orion",
    "downloadImage": false
  }'
```

### Get All Observations
```bash
curl http://localhost:8080/api/star-charts/observations
```

### Health Check
```bash
curl http://localhost:8080/api/star-charts/health
```

## ⚙️ Environment Variables

### Backend
```bash
ASTRONOMY_API_KEY=your_key_here
DATABASE_URL=jdbc:postgresql://localhost:5432/astrolog
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=password
STORAGE_TYPE=local
STORAGE_LOCAL_PATH=./uploads
```

### Frontend
```bash
VITE_API_BASE_URL=http://localhost:8080/api
```

## 📋 Troubleshooting

| Problem | Solution |
|---------|----------|
| Port 8080 in use | Change `server.port` in properties |
| Port 3000 in use | Change port in `vite.config.js` |
| Database connection fails | Verify PostgreSQL running, check credentials |
| API key error | Verify key in application.properties |
| CORS error | Check `cors.allowed-origins` in properties |
| Star chart not showing | Check browser console, verify API key |
| npm install fails | Run `npm cache clean --force` |

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| README.md | Project overview |
| SETUP.md | Detailed setup guide |
| PROJECT_SUMMARY.md | Complete project description |
| backend/README.md | Backend documentation |
| frontend/README.md | Frontend documentation |
| .env.example | Configuration template |

## 🎯 Next Steps

1. ✅ Install prerequisites
2. ✅ Get AstronomyAPI key
3. ✅ Configure application.properties
4. ✅ Start backend: `mvn spring-boot:run`
5. ✅ Start frontend: `npm run dev`
6. ✅ Open http://localhost:3000
7. ✅ Generate your first star chart!

## 💡 Tips

- **Local Database**: Use H2 during development (no setup needed)
- **API Key**: Store in environment variables for security
- **Images**: Keep `downloadImage: false` during testing (faster)
- **CORS**: Adjust `cors.allowed-origins` for your frontend URL
- **Logging**: Check `application.properties` for log level

## 🚀 Deployment

### Docker
```bash
docker-compose up
```

### Production
See individual README files:
- `backend/README.md` - Backend deployment
- `frontend/README.md` - Frontend deployment

## 📞 Support

- Check **SETUP.md** for detailed instructions
- See component README files for architecture
- Review source code comments for implementation details
- Check browser console and backend logs for errors

## 🎓 Learning Resources

- Spring Boot: https://spring.io/projects/spring-boot
- React: https://react.dev/
- AstronomyAPI: https://astronomyapi.com/docs
- Docker: https://docs.docker.com/

---

**Ready to explore the stars? 🌟**

