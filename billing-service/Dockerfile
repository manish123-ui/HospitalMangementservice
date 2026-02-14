FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy module pom files
COPY billing-service/pom.xml billing-service/pom.xml
COPY Patient-service/pom.xml Patient-service/pom.xml

# Download dependencies
RUN mvn -pl billing-service -am dependency:go-offline -B

# Copy source
COPY billing-service/src billing-service/src

# Build only billing-service
RUN mvn -pl billing-service -am clean package -DskipTests


FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/billing-service/target/billing-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4001
ENTRYPOINT ["java","-jar","app.jar"]


