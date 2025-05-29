FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY MartAuth/target/*.jar app.jar

EXPOSE 8765

ENTRYPOINT ["java", "-jar", "app.jar"]