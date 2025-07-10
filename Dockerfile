# Start with a base image that has Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose port (Render uses PORT env variable)
EXPOSE 8080

# Run the jar
CMD ["sh", "-c", "java -jar target/*.jar"]
