# First stage: Build the application
FROM maven:3.8.5-openjdk-17 AS build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Second stage: Run the application
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/Jobluu-0.0.1-SNAPSHOT.jar Jobluu.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","Jobluu.jar"]
