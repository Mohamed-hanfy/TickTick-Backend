FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN java --version
RUN chmod +x ./mvnw

ENV DB_PASSWORD

RUN ./mvnw package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "/app/target/ticktick-0.0.1-SNAPSHOT.jar"]