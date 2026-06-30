# ==========================================
# 1단계: 빌드 스테이지
# ==========================================
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test --no-daemon

# ==========================================
# 2단계: 실행 스테이지
# ==========================================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

# ⭐ [수정] 스프링 설정 무시 옵션(-Dspring.config.import.optional=true)을 추가하여 .env 관련 로딩 크래시를 원천 차단합니다.
ENTRYPOINT ["java", "-Dspring.config.import.optional=true", "-jar", "/app/app.jar"]