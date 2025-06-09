# Use OpenJDK 17 JRE Alpine version
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY ./pom.xml ./pom.xml

COPY ./src ./src

# Copy the pre-packaged JAR file
COPY ./target/*.jar app.jar

# Expose the port (default Gateway port is 8082)
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
