# AstroLog - Architecture Diagram

**Status**: ✅ Complete, live, and on GitHub
**GitHub**: https://github.com/gazzdenyer/Astrolog
**Backend**: Running on port 8080 ✅
**Frontend**: Running on port 3000 ✅
**Build**: All issues resolved ✅

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                         USER BROWSER                                 │
│                    (http://localhost:3000)                           │
│                                                                       │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │                     REACT FRONTEND                            │   │
│  │  ┌────────────────────────────────────────────────────────┐  │   │
│  │  │  App Component (Layout & Routing)                      │  │   │
│  │  │  ├─ StarChartForm (User Input)                         │  │   │
│  │  │  │  └─ Validation & Submit                             │  │   │
│  │  │  ├─ StarChartDisplay (Chart Viewer)                    │  │   │
│  │  │  │  └─ Image Display & Details                         │  │   │
│  │  │  └─ ObservationLog (History)                           │  │   │
│  │  │     └─ Search & Delete                                 │  │   │
│  │  └────────────────────────────────────────────────────────┘  │   │
│  │                                                                │   │
│  │  ┌────────────────────────────────────────────────────────┐  │   │
│  │  │           ObservationContext (State)                   │  │   │
│  │  │  ├─ observations[]                                     │  │   │
│  │  │  ├─ currentObservation                                 │  │   │
│  │  │  ├─ loading state                                      │  │   │
│  │  │  └─ error messages                                     │  │   │
│  │  └────────────────────────────────────────────────────────┘  │   │
│  │                                                                │   │
│  │  ┌────────────────────────────────────────────────────────┐  │   │
│  │  │        starChartAPI Service (Axios)                    │  │   │
│  │  │  ├─ generateStarChart()                                │  │   │
│  │  │  ├─ getAllObservations()                               │  │   │
│  │  │  ├─ searchObservations()                               │  │   │
│  │  │  └─ deleteObservation()                                │  │   │
│  │  └────────────────────────────────────────────────────────┘  │   │
│  │                                                                │   │
│  │  Styling: CSS3 (Dark Theme)                                   │   │
│  │  - StarChartForm.css                                          │   │
│  │  - StarChartDisplay.css                                       │   │
│  │  - ObservationLog.css                                         │   │
│  │  - index.css (Global)                                         │   │
│  └──────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
                              │
                              │ HTTPS/REST
                              │ (JSON)
                              ▼
┌─────────────────────────────────────────────────────────────────────┐
│              SPRING BOOT BACKEND (port 8080)                        │
│          Context Path: /api/star-charts                             │
│                                                                      │
│  ┌───────────────────────────────────────────────────────────────┐ │
│  │              StarChartController                              │ │
│  │  ┌─────────────────────────────────────────────────────────┐ │ │
│  │  │ Endpoints:                                              │ │ │
│  │  │ POST   /generate                (Create)               │ │ │
│  │  │ GET    /observations            (Read All)             │ │ │
│  │  │ GET    /observations/{id}       (Read One)             │ │ │
│  │  │ GET    /observations/location/* (Query)                │ │ │
│  │  │ GET    /observations/target/*   (Query)                │ │ │
│  │  │ PUT    /observations/{id}       (Update)               │ │ │
│  │  │ DELETE /observations/{id}       (Delete)               │ │ │
│  │  │ GET    /health                  (Health)               │ │ │
│  │  └─────────────────────────────────────────────────────────┘ │ │
│  └───────────────────────────────────────────────────────────────┘ │
│                              │                                      │
│                              ▼                                      │
│  ┌───────────────────────────────────────────────────────────────┐ │
│  │           StarChartService (Business Logic)                   │ │
│  │                                                                │ │
│  │  generateStarChart()  ──────────┐                             │ │
│  │  getAllObservations()           │                             │ │
│  │  getById()                      ├─ Coordinates & Orchestrate │ │
│  │  updateObservation()            │                             │ │
│  │  deleteObservation()  ──────────┘                             │ │
│  │                                                                │ │
│  │  Tasks:                                                        │ │
│  │  1. Validate input                                             │ │
│  │  2. Call AstronomyAPI                                          │ │
│  │  3. Download images (optional)                                 │ │
│  │  4. Save to database                                           │ │
│  │  5. Return response                                            │ │
│  └───────────────────────────────────────────────────────────────┘ │
│                              │                                      │
│         ┌────────────────────┼────────────────────┐               │
│         │                    │                    │                │
│         ▼                    ▼                    ▼               │
│  ┌────────────────┐  ┌──────────────────┐  ┌──────────────────┐ │
│  │AstronomyAPI    │  │ImageStorage      │  │Observation       │ │
│  │Service         │  │Service           │  │Repository        │ │
│  │                │  │                  │  │                  │ │
│  │- Auth          │  │- Download image  │  │- CRUD ops        │ │
│  │- Call API      │  │- Save local      │  │- Queries         │ │
│  │- Parse JSON    │  │- Upload S3       │  │- JPA methods     │ │
│  │- Handle errors │  │- Delete          │  │                  │ │
│  └────────────────┘  └──────────────────┘  └──────────────────┘ │
│         │                    │                    │                │
│         │                    ▼                    │               │
│         │             ┌──────────────┐            │               │
│         │             │ File System  │            │               │
│         │             │  ./uploads/  │            │               │
│         │             └──────────────┘            │               │
│         │                                         ▼               │
│         │                          ┌────────────────────────────┐ │
│         │                          │    Database Entity         │ │
│         │                          │    (Observation.java)      │ │
│         │                          │                            │ │
│         │                          │  @Entity                   │ │
│         │                          │  class Observation {       │ │
│         │                          │    - id (Long)             │ │
│         │                          │    - location (String)     │ │
│         │                          │    - latitude (Double)     │ │
│         │                          │    - longitude (Double)    │ │
│         │                          │    - observationDateTime   │ │
│         │                          │    - target (String)       │ │
│         │                          │    - chartUrl (String)     │ │
│         │                          │    - localImagePath        │ │
│         │                          │    - s3ImageUrl            │ │
│         │                          │    - notes (String)        │ │
│         │                          │    - createdAt             │ │
│         │                          │    - updatedAt             │ │
│         │                          │  }                         │ │
│         │                          └────────────────────────────┘ │
│         │                                         ▼               │
│         ▼                                         │               │
│  ┌────────────────────────────────┐             │               │
│  │ AstronomyAPI                   │             │               │
│  │ https://api.astronomyapi.com   │             │               │
│  │                                │             │               │
│  │ POST /api/v2/studio/star-chart │             │               │
│  │ Parameters:                    │             │               │
│  │ - latitude                     │             │               │
│  │ - longitude                    │             │               │
│  │ - date                         │             │               │
│  │ - style (navy/dark)            │             │               │
│  │ - observer_color               │             │               │
│  │                                │             │               │
│  │ Returns: Image URL             │             │               │
│  └────────────────────────────────┘             │               │
│                                                  │               │
│                                    PostgreSQL   │               │
│                                    Database     │               │
│                                                  ▼               │
│                        ┌──────────────────────────────────────┐ │
│                        │  observations Table                  │ │
│                        │  ┌──────────────────────────────────┤ │
│                        │  │ id                   (BIGINT)    │ │
│                        │  │ location             (VARCHAR)   │ │
│                        │  │ latitude             (DOUBLE)    │ │
│                        │  │ longitude            (DOUBLE)    │ │
│                        │  │ observation_datetime (TIMESTAMP) │ │
│                        │  │ target               (VARCHAR)   │ │
│                        │  │ chart_url            (VARCHAR)   │ │
│                        │  │ local_image_path     (VARCHAR)   │ │
│                        │  │ s3_image_url         (VARCHAR)   │ │
│                        │  │ notes                (TEXT)      │ │
│                        │  │ created_at           (TIMESTAMP) │ │
│                        │  │ updated_at           (TIMESTAMP) │ │
│                        │  └──────────────────────────────────┤ │
│                        └──────────────────────────────────────┘ │
│                                                  ▲               │
│                                    JDBC/Hibernate│               │
└─────────────────────────────────────────────────┼───────────────┘
                                                  │
                                          ┌───────┴─────────┐
                                          │                 │
                                     PostgreSQL         H2 Database
                                     (Production)       (Development)
                                          
```

---

## 🔄 Data Flow Diagram

### Scenario: Generate Star Chart

```
User Input (Form)
    │
    ├─ Location: "New York"
    ├─ Latitude: 40.7128
    ├─ Longitude: -74.0060
    ├─ DateTime: 2026-04-05 20:30:00
    ├─ Target: "Orion"
    └─ Notes: "Clear night"
    │
    ▼
┌─────────────────────────┐
│  StarChartForm Submit   │
│  - Validate form        │
│  - Show loading state   │
└─────────────────────────┘
    │
    ▼
┌─────────────────────────────────────────────────┐
│  axios.post('/api/star-charts/generate', data)  │
└─────────────────────────────────────────────────┘
    │
    ▼ (HTTP POST + JSON)
┌─────────────────────────────────────────────────┐
│  StarChartController                            │
│  .generateStarChart(@RequestBody request)       │
└─────────────────────────────────────────────────┘
    │
    ▼
┌─────────────────────────────────────────────────┐
│  StarChartService                               │
│  .generateStarChart(request)                    │
└─────────────────────────────────────────────────┘
    │
    ├─ Call AstronomyApiService
    │       │
    │       ▼
    │   ┌─────────────────────────────┐
    │   │ Build API URL:              │
    │   │ lat=40.7128&lon=-74.0060    │
    │   │ &date=2026-04-05T20:30:00   │
    │   └─────────────────────────────┘
    │       │
    │       ▼
    │   ┌─────────────────────────────┐
    │   │ Call AstronomyAPI           │
    │   │ Auth: Bearer {API_KEY}      │
    │   └─────────────────────────────┘
    │       │
    │       ▼
    │   ┌─────────────────────────────┐
    │   │ Return: Image URL           │
    │   │ https://...../chart.png     │
    │   └─────────────────────────────┘
    │       │
    ├───────┘
    │
    ├─ Save to Database (optional: download image)
    │       │
    │       ▼
    │   ┌─────────────────────────────┐
    │   │ Create Observation Entity   │
    │   │ - Set all fields            │
    │   │ - Auto-set timestamps       │
    │   └─────────────────────────────┘
    │       │
    │       ▼
    │   ┌─────────────────────────────┐
    │   │ Save via JPA Repository     │
    │   └─────────────────────────────┘
    │       │
    │       ▼
    │   ┌─────────────────────────────┐
    │   │ INSERT into PostgreSQL      │
    │   │ observations table          │
    │   └─────────────────────────────┘
    │       │
    ▼───────┘
┌─────────────────────────────────────────────────┐
│  Return StarChartResponse                       │
│  {                                              │
│    "observationId": 1,                          │
│    "location": "New York",                      │
│    "latitude": 40.7128,                         │
│    "longitude": -74.0060,                       │
│    "observationDateTime": "...",                │
│    "target": "Orion",                           │
│    "chartUrl": "https://..../chart.png",        │
│    "createdAt": "...",                          │
│    "notes": "Clear night"                       │
│  }                                              │
└─────────────────────────────────────────────────┘
    │
    ▼ (HTTP 201 CREATED + JSON)
┌─────────────────────────────────────────────────┐
│  React State Update                             │
│  - addObservation(response.data)                │
│  - setCurrentObservation(response.data)         │
│  - Clear form                                   │
│  - Show success message                         │
└─────────────────────────────────────────────────┘
    │
    ▼
┌─────────────────────────────────────────────────┐
│  Component Re-render                            │
│  ├─ StarChartDisplay updated                    │
│  │  └─ Shows chart image & details              │
│  └─ ObservationLog updated                      │
│     └─ New observation appears in table         │
└─────────────────────────────────────────────────┘
    │
    ▼
┌─────────────────────────────────────────────────┐
│  User sees:                                     │
│  ✓ Star chart image displayed                   │
│  ✓ Chart details shown                          │
│  ✓ Success message                              │
│  ✓ New observation in log                       │
└─────────────────────────────────────────────────┘
```

---

## 🗂️ File Dependency Diagram

```
Frontend Dependencies
═══════════════════════════════════════════════════

main.jsx
    │
    └─→ App.jsx
         ├─→ StarChartForm.jsx
         │    └─→ starChartAPI.js (axios)
         │
         ├─→ StarChartDisplay.jsx
         │    └─→ (consumes context)
         │
         ├─→ ObservationLog.jsx
         │    └─→ starChartAPI.js (axios)
         │
         └─→ ObservationContext.jsx
              └─→ (provides global state)

CSS Dependencies
═══════════════════════════════════════════════════

index.css (global styles)
    ├─→ App.css
    ├─→ StarChartForm.css
    ├─→ StarChartDisplay.css
    └─→ ObservationLog.css


Backend Dependencies
═══════════════════════════════════════════════════

AstroLogApplication.java
    │
    └─→ StarChartController.java
         └─→ StarChartService.java
              ├─→ AstronomyApiService.java
              │    ├─→ RestTemplate
              │    └─→ Jackson ObjectMapper
              │
              ├─→ ImageStorageService.java
              │
              └─→ ObservationRepository.java
                   ├─→ Observation.java (Entity)
                   └─→ Database

Configuration
═══════════════════════════════════════════════════

pom.xml (Maven)
    └─→ All Dependencies

application.properties (Spring Boot)
    ├─→ CorsConfig.java
    ├─→ RestTemplateConfig.java
    └─→ Database Configuration
```

---

## 📡 Network Communication

```
Client (Browser)          Network          Server (Backend)
─────────────────         ───────          ────────────────

React App
  │
  │ HTTP/REST (JSON)
  ├─→ POST /api/star-charts/generate
  │                          ────────────────→ Spring Boot
  │                                           Processes
  │                          ←───────────────
  │ HTTP 201 CREATED (JSON)
  │
  └─ Update UI
     Display Chart


Periodic:
  │ HTTP/REST (JSON)
  ├─→ GET /api/star-charts/observations
  │                          ────────────────→ Spring Boot
  │                                           Database Query
  │                          ←───────────────
  │ HTTP 200 OK (JSON Array)
  │
  └─ Update List
     Show Observations


External:
  Spring Boot
     │
     │ HTTP/REST (JSON)
     ├─→ GET /api/v2/studio/star-chart
     │   Header: Authorization: Bearer {KEY}
     │                          ────────────────→ AstronomyAPI
     │                                           Generates Chart
     │                          ←───────────────
     │ HTTP 200 OK (Image URL)
     │
     └─ Save Response
        Store in Database
```

---

## 🔐 Authentication & Security Flow

```
┌──────────────────────────────────────┐
│     Frontend (No Authentication)      │
│     - Public API access              │
│     - CORS headers validated         │
└──────────────────────────────────────┘
           │
           │ CORS Policy Check
           ▼
┌──────────────────────────────────────┐
│     Spring Boot (CORS Validator)     │
│  - origin must match config          │
│  - methods must be allowed           │
│  - headers must be allowed           │
└──────────────────────────────────────┘
           │
           ├─ ✓ CORS OK
           │   └─→ Continue
           │
           └─ ✗ CORS FAIL
               └─→ 403 Forbidden
           │
           ▼
┌──────────────────────────────────────┐
│    Request Processing               │
│  - Validate input data              │
│  - Check constraints                │
└──────────────────────────────────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ AstronomyAPI (API Key Authentication)│
│  - Bearer Token in header           │
│  - API key = "Authorization"        │
│  - Validates permission             │
└──────────────────────────────────────┘
           │
           ├─ ✓ Valid Key
           │   └─→ Return Star Chart
           │
           └─ ✗ Invalid Key
               └─→ 401 Unauthorized
```

---

## 💾 Database Schema Relationships

```
┌─────────────────────────────────────────────────┐
│          observations Table                     │
├─────────────────────────────────────────────────┤
│ PK │ id (BIGINT)                               │
├────┼─────────────────────────────────────────────┤
│    │ location (VARCHAR)                        │
│    │ latitude (DOUBLE)                         │
│    │ longitude (DOUBLE)                        │
│    │ observation_datetime (TIMESTAMP)          │
│    │ target (VARCHAR)                          │
│    │ chart_url (VARCHAR)                       │
│    │ local_image_path (VARCHAR)                │
│    │ s3_image_url (VARCHAR)                    │
│    │ notes (TEXT)                              │
│    │ created_at (TIMESTAMP)                    │
│    │ updated_at (TIMESTAMP)                    │
└─────────────────────────────────────────────────┘

Indexes (for performance):
  └─ PRIMARY KEY: id
  └─ INDEX: created_at (sorting)
  └─ INDEX: location (filtering)
  └─ INDEX: target (filtering)

Queries:
  └─ SELECT * FROM observations ORDER BY created_at DESC
  └─ SELECT * FROM observations WHERE location = ?
  └─ SELECT * FROM observations WHERE target = ?
  └─ INSERT INTO observations (...)
  └─ UPDATE observations SET ... WHERE id = ?
  └─ DELETE FROM observations WHERE id = ?
```

---

## 🔄 Component Lifecycle

```
App Mounts
    │
    ├─→ ObservationProvider (Context)
    │
    ├─→ StarChartForm Mounts
    │    └─ Ready for user input
    │
    ├─→ StarChartDisplay Mounts
    │    └─ Shows placeholder (no observation)
    │
    └─→ ObservationLog Mounts
         └─ Fetch all observations
             │
             ▼ API Call
         Display in table


User Interaction (Generate Chart)
    │
    ├─→ StarChartForm.handleSubmit()
    │    │
    │    ├─ Validate form
    │    ├─ Call API
    │    │
    │    ├─ On Success:
    │    │  ├─ Update Context
    │    │  ├─ Update StarChartDisplay
    │    │  ├─ Update ObservationLog
    │    │  ├─ Clear form
    │    │  └─ Show success message
    │    │
    │    └─ On Error:
    │       └─ Show error message
    │
    └─→ All components re-render via Context


User Interaction (Delete)
    │
    ├─→ ObservationLog.handleDelete()
    │    │
    │    ├─ Confirm with user
    │    ├─ Call API DELETE
    │    │
    │    ├─ On Success:
    │    │  └─ Remove from list
    │    │
    │    └─ On Error:
    │       └─ Show error message
    │
    └─→ List re-renders
```

---

**End of Architecture Documentation**

For more details, see the individual README files in each directory.

