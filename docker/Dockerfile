
# maven install and build
FROM maven:3.6.0-jdk-11-slim AS build

COPY ../src /opt/app/src
COPY ./pom.xml /opt/app

RUN mvn -f /opt/app/pom.xml clean package


# running the server
FROM openjdk:11-jre-slim
COPY --from=build /opt/app/target/assignement-0.0.1-SNAPSHOT.jar /opt/builds/app.jar

# health checks
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl jq \
 && rm -rf /var/lib/apt/lists/*

ENV PORT 8090
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "/opt/builds/app.jar"]