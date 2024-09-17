# First stage: build the JAR file
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /SubscriptionCheck
COPY /SubscriptionCheck/pom.xml .
COPY /SubscriptionCheck/src ./src
RUN mvn clean package

# Second stage: use the JAR file from the build stage
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /SubscriptionCheck/target/SubscriptionCheck-0.0.1-SNAPSHOT.jar /app/byte.jar
ENTRYPOINT ["java", "-jar", "byte.jar"]
