# Use a lightweight Java image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Expose the application port (default Spring Boot port is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
