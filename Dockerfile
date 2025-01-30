# Stage 1: Build the application
FROM arm64v8/openjdk:17-jdk-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the src directory into the container
COPY pom.xml ./
COPY src ./src

# Run Maven to resolve dependencies and package the application
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM arm64v8/openjdk:17-jdk-slim

# Set the working directory in the runtime image
WORKDIR /app

# Copy the JAR file generated in the build stage to the final image
COPY --from=builder /app/target/receipt-processor.jar /app/app.jar

# Expose the port the app will run on
EXPOSE 8080

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
