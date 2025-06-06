# Use OpenJDK 17 JRE Alpine version for a lightweight runtime
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the pre-built JAR file into the image
COPY ./target/*.jar app.jar

# Expose the Eureka default port
EXPOSE 8761

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
