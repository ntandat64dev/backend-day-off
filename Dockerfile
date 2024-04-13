FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY backend-day-off-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","/back-end-day-off-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080