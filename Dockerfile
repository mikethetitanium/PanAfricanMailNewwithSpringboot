# Use OpenJDK 23 as the base image
FROM openjdk:23-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN ./mvnw clean package -DskipTests

# Copy the built jar
COPY target/PanAfricanMail-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (Render sets PORT as environment variable)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
