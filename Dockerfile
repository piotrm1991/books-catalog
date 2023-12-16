FROM maven:3.9.0 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
COPY checkstyle ./checkstyle
RUN mvn clean package
FROM openjdk:17
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/academy-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]
