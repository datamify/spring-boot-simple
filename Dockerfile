FROM gradle:7.0.2-jdk11 AS build
COPY . /workdir
WORKDIR /workdir
RUN gradle clean bootJar --no-daemon

FROM openjdk:11.0.11-jre-slim
COPY --from=build /workdir/build/libs/spring-boot-simple*.jar /app.jar
CMD ["java", "-jar", "-Dspring.datasource.url=jdbc:postgresql://postgres:5432/spring_boot_simple", "/app.jar"]
