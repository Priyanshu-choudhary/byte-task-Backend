# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY SubscriptionCheck/target/SubscriptionCheck-0.0.1-SNAPSHOT.jar /app/byte.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "byte.jar"]

# Expose port 8080 (or whatever port your application uses)
EXPOSE 8080
