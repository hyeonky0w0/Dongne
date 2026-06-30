# 1. Java 실행 환경 가져오기
FROM market-mirror.cray.com/openjdk:17-jdk-slim

# 2. 빌드된 jar 파일을 컨테이너 내부로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. 포트 지정
EXPOSE 8080

# 4. 앱 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]