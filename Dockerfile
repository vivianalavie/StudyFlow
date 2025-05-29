# Build stage
FROM maven:3.9.9-amazoncorretto-24 AS builder
WORKDIR /app
COPY . .
RUN mvn -B clean package -DskipTests=true

# Run stage
FROM openjdk:24-jdk-slim
WORKDIR /app

# Copy Spring Boot fat JAR (by default named: studyflow-<version>.jar)
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]