FROM maven:3.6.3-openjdk-17 AS build
WORKDIR /build/
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src/
RUN mvn clean package -DskipTests
FROM openjdk:17-slim
WORKDIR /app/
COPY --from=build /build/target/*.jar encurtador.jar
CMD ["java", "-jar", "encurtador.jar"]
