# ==========================================
# 1단계: 빌드 스테이지 (Gradle을 이용해 코드를 컴파일합니다)
# ==========================================
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

# 소스코드를 컨테이너 내부로 복사
COPY . .

# Gradle을 이용해 테스트는 제외하고 실행 가능한 .jar 파일 빌드
RUN ./gradlew bootJar -x test

# ==========================================
# 2단계: 실행 스테이지 (빌드된 결과물만 가져와서 실행합니다)
# ==========================================
FROM gcr.io/distroless/java17-debian11
WORKDIR /app

# 1단계(builder)에서 생성된 진짜 .jar 파일만 쏙 빼와서 복사합니다.
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]