# 1. Java 실행 환경 가져오기 (본인의 자바 버전에 맞추세요. 예: 17 버전)
FROM openjdk:17-jdk-slim

# 2. 빌드된 jar 파일을 컨테이너 내부로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. 포트 지정 (Cloud Run은 기본적으로 8080 포트를 바라봅니다)
EXPOSE 8080

# 4. 앱 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]