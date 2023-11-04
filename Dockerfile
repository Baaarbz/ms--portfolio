FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/portfolio-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8000

ENV SPRING_PROFILES_ACTIVE=pro

CMD ["java", "-jar", "app.jar"]
