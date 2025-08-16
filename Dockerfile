# -------- Stage 1: Build the project using Maven Wrapper --------
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy Maven wrapper and config first (to leverage caching for dependencies)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Pre-download dependencies to speed up rebuilds
RUN ./mvnw dependency:go-offline -B

# Now copy the full source code
COPY src ./src

# Package the application (create a JAR file) - skipping tests for speed
RUN ./mvnw clean package -DskipTests

# -------- Stage 2: Run the application --------
FROM eclipse-temurin:17-jdk-alpine AS runner

# Set the working directory again in the final container
WORKDIR /app

# Copy the jar from the builder image
COPY --from=builder /app/target/*.jar patient-service-1.0.0.jar

# Expose port for Spring Boot app
EXPOSE 4001

# Run the app
ENTRYPOINT ["java", "-jar", "patient-service-1.0.0.jar"]