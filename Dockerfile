FROM amazoncorretto:17
WORKDIR /app
COPY target/weather-app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]