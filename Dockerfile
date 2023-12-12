FROM openjdk:17-alpine
LABEL authors="Ivan"

WORKDIR /app
COPY build/libs/home-0.0.1-SNAPSHOT.jar /app/home.jar
EXPOSE 8080

ENTRYPOINT ["top", "-b"]