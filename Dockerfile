
FROM jelastic/maven:3.9.5-openjdk-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN  mvn clean install


FROM openjdk:21-jdk
WORKDIR /app

COPY --from=build /app/target/movie-0.0.1-SNAPSHOT.jar ./movie-api.jar
EXPOSE 8080
CMD ["java", "-jar", "movie-api.jar"]
