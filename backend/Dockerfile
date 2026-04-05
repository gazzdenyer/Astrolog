# Build stage
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml
COPY pom.xml .

# Download dependencies
RUN mvn clean install -DskipTests

# Copy source code
COPY src ./src

# Build application
RUN mvn package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/target/astrolog-backend-1.0.0.jar app.jar

# Create uploads directory
RUN mkdir -p uploads

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/star-charts/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]

