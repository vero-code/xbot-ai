# --- Step 1: Building the application (Builder) ---
FROM maven:3.9-eclipse-temurin-21 AS builder

# Set the working directory for the build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the Java source code and the blockchain folder
COPY src ./src
COPY blockchain ./blockchain

# Build the JAR file. Maven will automatically place the compiled resources in target/classes
RUN mvn clean package -DskipTests


# --- Stage 2: Final image (Runner) ---
FROM eclipse-temurin:21-jre-jammy

# Set the working directory to run the application
WORKDIR /app

# Copy ONLY the built JAR file and the blockchain folder from the previous stage
COPY --from=builder /app/target/*.jar app.jar
COPY --from=builder /app/blockchain ./blockchain

# Open the port on which the application runs
EXPOSE 8080

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]