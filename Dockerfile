FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY target/todo-app-cicd-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]