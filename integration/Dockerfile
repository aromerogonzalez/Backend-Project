FROM eclipse-temurin:23-jdk
WORKDIR /app
EXPOSE 8050
COPY target/*.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
