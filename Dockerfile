# 1. 구글이 직접 관리하여 100% 신뢰할 수 있는 공식 오픈JDK 17 이미지로 교체합니다.
FROM gcr.io/distroless/java17-debian11

# 2. 빌드된 jar 파일을 컨테이너 내부로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. 포트 지정
EXPOSE 8080

# 4. 앱 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]