FROM maven:3.8.6 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:18-jdk-alpine
COPY --from=build /usr/src/app/target/qr-generator-0.0.1-SNAPSHOT.jar /usr/src/app/qr-generator-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/src/app/qr-generator-0.0.1-SNAPSHOT.jar"]
