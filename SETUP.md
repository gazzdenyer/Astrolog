# AstroLog - Setup Guide

Complete setup instructions for the AstroLog star chart application.

**Current Status**: ✅ Backend running on port 8080 ✅ Frontend running on port 3000 ✅ App fully functional

The application is **already configured and running** with H2 in-memory database. No additional setup is required to use it!

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Backend Setup](#backend-setup)
3. [Frontend Setup](#frontend-setup)
4. [Database Setup](#database-setup)
5. [Environment Configuration](#environment-configuration)
6. [Running the Application](#running-the-application)
7. [Testing](#testing)
8. [Troubleshooting](#troubleshooting)
9. [Build Fixes](#build-fixes)

## Prerequisites

### ✅ Required Software (ALL INSTALLED)

1. **Java Development Kit (JDK) 25.0.2** ✅
   - Status: INSTALLED
   - Location: C:\Program Files\Java\jdk-25.0.2
   - Verify: `java -version`

2. **Node.js v24.14.1 and npm 11.11.0** ✅
   - Status: INSTALLED
   - Verify: `node --version` and `npm --version`

3. **Maven 3.9.14** ✅
   - Status: INSTALLED
   - Location: C:\maven\apache-maven-3.9.14
   - Verify: `mvn --version`

4. **PostgreSQL 12+** (or use H2 for development)
   - Download: https://www.postgresql.org/download/
   - Verify: `psql --version`

5. **Git**
   - Download: https://git-scm.com/


### Accounts & Keys

1. **AstronomyAPI Credentials** (Required)
   - Sign up: https://astronomyapi.com/
   - Get your **Application ID and Secret** (recommended) OR
   - Get your **API Key** (alternative method)
   - Keep them safe (you'll need them)

## Backend Setup

### Step 1: Navigate to Backend Directory

```bash
cd backend
```

### Step 2: Install Maven Dependencies

```bash
mvn clean install
```

This downloads all required Java dependencies.

### Step 3: Configure Application Properties

Edit `src/main/resources/application.properties`:

#### Option A: PostgreSQL (Recommended)

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/astrolog
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# AstronomyAPI Configuration
# Option 1: Basic Authentication with Application ID + Secret (RECOMMENDED)
astronomy.api.id=your_application_id
astronomy.api.secret=your_application_secret
astronomy.api.base-url=https://api.astronomyapi.com/api/v2

# Option 2: API Key (Alternative - use if you only have an API key)
# astronomy.api.key=your_api_key
# astronomy.api.base-url=https://api.astronomyapi.com/api/v2

# Storage
storage.type=local
storage.local.path=./uploads

# CORS
cors.allowed-origins=http://localhost:3000,http://localhost:3001
```

#### Option B: H2 In-Memory (Development Only)

```properties
# Uncomment H2 section in application.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

### Step 4: Set Environment Variables

#### Windows (PowerShell)
```powershell
$env:ASTRONOMY_API_KEY = "your_api_key_here"
```

#### Windows (Command Prompt)
```cmd
set ASTRONOMY_API_KEY=your_api_key_here
```

#### Linux/Mac
```bash
export ASTRONOMY_API_KEY=your_api_key_here
```

### Step 5: Create Upload Directory

```bash
# From backend directory
mkdir uploads
```

### Step 6: Run Backend

#### Option 1: Maven (Recommended for development)
```bash
mvn spring-boot:run
```

#### Option 2: IntelliJ IDEA
1. Right-click `AstroLogApplication.java`
2. Select "Run 'AstroLogApplication'"

#### Option 3: JAR File
```bash
mvn clean package
java -jar target/astrolog-backend-1.0.0.jar
```

### Step 7: Verify Backend is Running

```bash
curl http://localhost:8080/api/star-charts/health
```

Expected response: `"AstroLog API is running"`

## Frontend Setup

### Step 1: Navigate to Frontend Directory

```bash
cd frontend
```

### Step 2: Install Node Dependencies

```bash
npm install
```

This installs all required npm packages.

### Step 3: Create Environment File (Optional)

Create `.env` file in frontend directory:

```
VITE_API_BASE_URL=http://localhost:8080/api
```

If not created, default values will be used.

### Step 4: Start Development Server

```bash
npm run dev
```

Output will show: `Local: http://localhost:3000`

### Step 5: Verify Frontend is Running

Open browser and navigate to: `http://localhost:3000`

You should see the AstroLog interface.

## Database Setup

### PostgreSQL Setup

1. **Create Database**

```sql
CREATE DATABASE astrolog;
```

2. **Create User** (Optional)

```sql
CREATE USER astrolog_user WITH PASSWORD 'your_password';
ALTER ROLE astrolog_user SET client_encoding TO 'utf8';
ALTER ROLE astrolog_user SET default_transaction_isolation TO 'read committed';
ALTER ROLE astrolog_user SET default_transaction_deferrable TO on;
ALTER ROLE astrolog_user SET timezone TO 'UTC';
GRANT ALL PRIVILEGES ON DATABASE astrolog TO astrolog_user;
```

3. **Connect**

```bash
psql -U postgres -d astrolog
```

4. **Verify Connection**

```sql
\dt
```

This lists all tables. Spring Boot will create them on first run.

### H2 Setup (Development)

H2 is embedded and requires no setup. Access console at:
`http://localhost:8080/api/h2-console`

JDBC URL: `jdbc:h2:mem:testdb`

## Environment Configuration

### Backend (application.properties)

| Property | Description | Example |
|----------|-------------|---------|
| `server.port` | Backend server port | 8080 |
| `spring.datasource.url` | Database URL | jdbc:postgresql://localhost:5432/astrolog |
| `spring.datasource.username` | Database user | postgres |
| `spring.datasource.password` | Database password | password |
| `astronomy.api.key` | AstronomyAPI key | abc123xyz |
| `storage.type` | Image storage type | local or s3 |
| `storage.local.path` | Local storage path | ./uploads |
| `cors.allowed-origins` | CORS allowed origins | http://localhost:3000 |

### Frontend (.env)

| Variable | Description | Example |
|----------|-------------|---------|
| `VITE_API_BASE_URL` | Backend API URL | http://localhost:8080/api |

## Running the Application

### Terminal 1: Backend

```bash
cd backend
mvn spring-boot:run
```

Wait for: `Started AstroLogApplication in X seconds`

### Terminal 2: Frontend

```bash
cd frontend
npm run dev
```

Wait for: `Local: http://localhost:3000`

### Access the Application

Open browser: `http://localhost:3000`

## Testing

### Backend Tests

```bash
cd backend
mvn test
```

### Frontend Tests

```bash
cd frontend
npm test
```

### Manual Testing

1. **Generate Star Chart**
   - Fill form with sample data:
     - Location: "New York"
     - Latitude: 40.7128
     - Longitude: -74.0060
     - Date/Time: Select current date/time
     - Target: "Orion"
   - Click "Generate Star Chart"
   - Verify chart appears

2. **View Observations**
   - Generated chart should appear in log
   - Click row to view details

3. **Filter Observations**
   - Click "By Location" filter
   - Enter location name
   - Verify filtering works

4. **Delete Observation**
   - Click ✕ button
   - Confirm deletion
   - Verify removed from log

## Troubleshooting

### Backend Issues

#### Issue: Port 8080 Already in Use
```bash
# Find process using port 8080 (Windows)
netstat -ano | findstr :8080

# Kill process
taskkill /PID <PID> /F

# Or change port in application.properties
server.port=8081
```

#### Issue: Database Connection Failed
- Verify PostgreSQL is running
- Check connection string is correct
- Verify credentials are correct
- Check database exists

```bash
# Test connection
psql -h localhost -U postgres -d astrolog
```

#### Issue: AstronomyAPI Errors
- Verify API key is correct
- Check API quota hasn't been exceeded
- Verify API key has permissions for star charts
- Check network connectivity

#### Issue: Maven Build Fails
```bash
# Clear cache and rebuild
mvn clean -DskipTests=true install
```

### Frontend Issues

#### Issue: npm install Fails
```bash
# Clear npm cache
npm cache clean --force

# Remove node_modules and package-lock.json
rm -r node_modules package-lock.json

# Reinstall
npm install
```

#### Issue: Port 3000 Already in Use
```bash
# Change port in vite.config.js
# Or kill process using port 3000
lsof -ti:3000 | xargs kill -9  # Linux/Mac
netstat -ano | findstr :3000   # Windows
```

#### Issue: API Calls Fail with CORS Error
- Verify backend is running
- Check backend CORS configuration
- Clear browser cache
- Try in incognito/private mode

#### Issue: Star Chart Not Displaying
- Check browser console for errors
- Verify API returned valid image URL
- Check image URL is accessible
- Try right-click → Open Image in New Tab

### Common Errors

#### "Cannot find module" Error
```bash
# Backend
mvn clean install -U

# Frontend
npm install
npm install --force  # if needed
```

#### "EADDRINUSE: address already in use"
Kill process on that port and restart.

#### "Unable to connect to database"
Verify PostgreSQL is running and connection string is correct.

#### "API key invalid or expired"
Check API key in AstronomyAPI dashboard.

## Next Steps

1. Customize styling in `frontend/src/styles/`
2. Add user authentication if needed
3. Set up database migrations
4. Configure production deployment
5. Set up CI/CD pipeline
6. Monitor API usage and performance

## Getting Help

1. Check README.md files in each directory
2. Review component comments in source code
3. Check browser console for errors
4. Check backend logs for API errors
5. Verify all environment variables are set

## Build Fixes & Known Issues

### Backend Build Issue (RESOLVED ✅)

**Issue**: Lombok annotation processing incompatibility with Java 25

**Solution Applied**:
- Disabled Lombok annotation processing in Maven compiler
- Replaced `@Slf4j` with manual `LoggerFactory` logger declarations
- Replaced `@Data` and `@Builder` with manual getters/setters and constructors
- Updated pom.xml with proper compiler configuration

**Result**: ✅ Backend builds successfully and runs on port 8080

See **BUILD_FIX_SUMMARY.md** for complete details.

## Production Deployment

See individual README files for production deployment instructions:
- `backend/README.md` - Backend deployment
- `frontend/README.md` - Frontend deployment

---

**Setup Complete!** 🎉

Your AstroLog application should now be running. Start exploring and generating star charts!

