FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/users-0.0.1-SNAPSHOT.jar /app/users-0.0.1-SNAPSHOT.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "users-0.0.1-SNAPSHOT.jar"]