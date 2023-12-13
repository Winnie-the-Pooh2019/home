FROM openjdk:17-alpine
LABEL authors="Ivan Duvanov"

ENV JAVA_OPTS="-Dspring.profiles.active=dev"
WORKDIR /app
COPY build/libs/*SNAPSHOT.jar /app/home.jar
EXPOSE 8080

CMD java $JAVA_OPTS -jar /app/home.jar