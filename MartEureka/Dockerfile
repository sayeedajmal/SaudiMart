FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY MartEureka/target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]