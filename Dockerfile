FROM maven:3.9.9-eclipse-temurin-23-noble
WORKDIR /app

# Copy project files and build
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Run the application
EXPOSE 8080
CMD ["java", "-jar", "target/MavenCC-1.0-SNAPSHOT.jar", "server", "src/main/resources/config.yml"]
