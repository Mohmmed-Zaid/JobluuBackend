# First stage: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Second stage: Run the application
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/Jobluu-0.0.1-SNAPSHOT.jar Jobluu.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Jobluu.jar"]
