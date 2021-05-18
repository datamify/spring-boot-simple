FROM openjdk:11.0.11-jdk

COPY . /workdir
WORKDIR /workdir

RUN ./gradlew clean bootJar
COPY build/libs/spring-boot-simple*.jar /app.jar
CMD ["java", "-jar", "-Dspring.datasource.url=jdbc:postgresql://postgres:5432/spring_boot_simple", "/app.jar"]
