# ---- Build Stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first (better caching)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy built jar
COPY --from=build /app/target/*.jar app.jar

# Render will override PORT automatically
EXPOSE 8080

# ✅ MOST RELIABLE startup command
CMD ["java", "-jar", "app.jar"]