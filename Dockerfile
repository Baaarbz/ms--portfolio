FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/portfolio-1.0.0.jar app.jar

EXPOSE 8000

ENV SPRING_PROFILES_ACTIVE=pro

CMD ["java", "-jar", "-Dspring.datasource.username=${DB_USERNAME}", "-Dspring.datasource.password=${DB_PASSWORD}", "-Dcom.barbzdev.auth.usr=${AUTH_USR}", "-Dcom.barbzdev.auth.pwd=${AUTH_PWD}", "app.jar"]
