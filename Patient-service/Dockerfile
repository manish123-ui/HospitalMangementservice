FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
# Copy parent pom
COPY pom.xml .
# Copy module pom
COPY billing-service/pom.xml billing-service/pom.xml
COPY Patient-service/pom.xml Patient-service/pom.xml
# Resolve deps (parent now visible)
RUN mvn -pl Patient-service -am dependency:go-offline -B
# Copy source
COPY Patient-service/src Patient-service/src
# Build only patient-service
RUN mvn clean package -DskipTests
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/Patient-service/target/patient-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 4000
ENTRYPOINT ["java","-jar","app.jar"]
