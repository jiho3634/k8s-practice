#멀티 스테이지 빌드 방법 사용
#첫 번째 스테이지
FROM openjdk:11 AS stage1
WORKDIR /app
#/app/gradlew로 생성
COPY gradlew .
#RUN chmod +x gradlew
#/app/gradle 폴더로 생성
COPY gradle gradle
COPY src src
COPY build.gradle .
COPY settings.gradle .
RUN chmod 777 gradlew
ENV GRADLE_OPTS="-Dscan.acceptTermsOfService=yes"
RUN ./gradlew bootJar --stacktrace --info --scan
RUN ./gradlew bootJar

#두 번째 스테이지

FROM openjdk:11
WORKDIR /app
#stage1에 있는 jar를 statge2의 app.jar라는 이름으로 COPY
COPY --from=stage1 /app/build/libs/*.jar app.jar

#CMD 또는 ENTRYPOINT를 통해 컨테이너를 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

#docker 컨테이너 내에서 밖의 전체 host를 지칭하는 도메인 : host.docker.internal
#docker run -d -p 8081:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/board spring_test:latest

#docker 컨테이너 실행 시에 볼륨을 설정할 때에는 -v 옵션 사용
#docker run -d -p 8081:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/board -v /host/path:/container/path spring_test:latest
#윈도우 호스트의 경로는 unix 스타일로.
#docker run -d -p 8081:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/board -v /c/Users/Playdata/Downloads/docker-practice/basic2/demo/logs:/app/logs/ spring_test:latest
