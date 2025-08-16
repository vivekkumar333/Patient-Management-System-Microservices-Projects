# -------- Stage 1: Build the project using Maven Wrapper --------
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw clean package -DskipTests

# -------- Stage 2: Run the application --------
FROM eclipse-temurin:17-jdk-alpine AS runner
WORKDIR /app
COPY --from=builder /app/target/*.jar gateway-service-1.0.0.jar
EXPOSE 4005
ENTRYPOINT ["java", "-jar", "gateway-service-1.0.0.jar"]