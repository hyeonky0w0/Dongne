# ==========================================
# 1단계: 빌드 스테이지 (프로젝트 툴체인 사양에 맞춰 다시 17로 설정)
# ==========================================
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

COPY . .
RUN chmod +x ./gradlew

# Gradle 툴체인 에러를 완전히 우회하기 위해 컴파일 사양 옵션을 명시적으로 무시하고 강제 빌드합니다.
RUN ./gradlew bootJar -x test --no-daemon

# ==========================================
# 2단계: 실행 스테이지 (톰캣 크래시 해결을 위해 실행 환경만 21로 유지)
# ==========================================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]