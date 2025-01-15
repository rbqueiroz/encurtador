FROM maven:3.6.3-openjdk-17 as build
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /build/target/*.jar /app/encurtador.jar
CMD ["java", "-jar", "encurtador.jar"]