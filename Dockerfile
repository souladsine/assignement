# For Java 8, try this
# FROM openjdk:8-jdk-alpine

FROM maven:3.6.0-jdk-11-slim AS build

COPY src /opt/app/src
COPY pom.xml /opt/app

RUN mvn -f /opt/app/pom.xml clean package


FROM openjdk:11-jre-slim
COPY --from=build /opt/app/target/assignement-0.0.1-SNAPSHOT.jar /opt/builds/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/build/app.jar"]