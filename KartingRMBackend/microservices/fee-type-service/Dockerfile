# Usando OpenJDK 17 JRE Alpine
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY ./pom.xml ./pom.xml

COPY ./src ./src

# Copiar el JAR pre packaged, se necesita correr mvn clean package antes
COPY ./target/*.jar app.jar

# Expose default port (Opcional, cambia a dinamico en runtime)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
