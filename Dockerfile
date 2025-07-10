# Start with Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy everything into container
COPY . .

# Set execute permission for Maven wrapper
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose default Spring Boot port
EXPOSE 8080

# Run the jar
CMD ["sh", "-c", "java -jar target/*.jar"]
