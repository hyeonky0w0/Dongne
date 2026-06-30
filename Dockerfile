# 1단계: 빌드 스테이지
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test --no-daemon

# 2단계: 실행 스테이지
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.config.import.optional=true", "-jar", "/app/app.jar"]