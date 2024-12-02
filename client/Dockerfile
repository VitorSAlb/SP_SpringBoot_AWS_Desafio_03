#FROM maven:3-eclipse-temurin-21 AS build
#WORKDIR /app
#COPY pom.xml ./
#COPY src ./src
#RUN mvn package

FROM eclipse-temurin:21-jre-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]