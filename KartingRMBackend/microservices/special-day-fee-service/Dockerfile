# Use OpenJDK 17 JRE Alpine version
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the Maven project file
COPY ./pom.xml ./pom.xml

# Copy the src directory
COPY ./src ./src

# Copy the pre-packaged JAR file
COPY ./target/*.jar app.jar

# Expose a default port (optional, for reference; dynamic ports are handled at runtime)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
