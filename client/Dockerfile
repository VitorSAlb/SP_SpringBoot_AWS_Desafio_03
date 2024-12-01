FROM openjdk:21-alpine
RUN apk add --no-cache bash curl
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
]