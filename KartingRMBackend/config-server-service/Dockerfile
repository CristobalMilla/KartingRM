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

# Expose the port (default Config Server port is 8888)
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
