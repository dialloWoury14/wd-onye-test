FROM openjdk:11-jre-slim-bullseye
VOLUME /tmp
ARG JAR_FILE
COPY build/libs/backend-test-*-all.jar app.jar
COPY conf conf
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
