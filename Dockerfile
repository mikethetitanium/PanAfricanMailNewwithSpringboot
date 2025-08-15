# Use an official Java runtime as parent image
FROM eclipse-temurin:23-jdk-jammy

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy source code
COPY src ./src

# Build the JAR inside the container
RUN ./mvnw clean package -DskipTests

# Copy the built JAR to a standard name
RUN cp target/PanAfricanMail-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java","-jar","app.jar"]
