FROM gradle:9.4.0-jdk25 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon

COPY src src
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:25-jre

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar"]
