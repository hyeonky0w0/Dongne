# ==========================================
# 1단계: 빌드 스테이지 (Java 21로 빌드 환경 업그레이드)
# ==========================================
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test

# ==========================================
# 2단계: 실행 스테이지 (Java 21 런타임 환경으로 일치)
# ==========================================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]