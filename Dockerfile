# ==========================================
# 1단계: 빌드 스테이지
# ==========================================
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

# 소스코드를 컨테이너 내부로 복사
COPY . .

# ⭐ [추가] gradlew 파일에 리눅스 실행 권한(+x)을 강제로 부여합니다.
RUN chmod +x ./gradlew

# 이제 권한 막힘 없이 정상적으로 컴파일이 진행됩니다.
RUN ./gradlew bootJar -x test

# ==========================================
# 2단계: 실행 스테이지
# ==========================================
FROM gcr.io/distroless/java17-debian11
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]