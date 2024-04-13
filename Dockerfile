FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN ./mvnw package
COPY /target/*.jar back-end-day-off-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/back-end-day-off-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080