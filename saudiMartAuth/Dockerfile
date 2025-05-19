FROM maven:3.8.4-openjdk-17 AS build
COPY . /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17-jdk-slim
COPY --from=build /usr/src/app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]

