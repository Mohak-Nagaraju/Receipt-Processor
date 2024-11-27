# Receipt Processor Java Application

This project is a Java-based receipt processing application that has been Dockerized. It uses Maven for dependency management and packaging, and can be run inside a Docker container.

## Prerequisites

- Docker must be installed and running on your machine.  
  [Install Docker](https://docs.docker.com/get-docker/)

## Project Structure

- **Dockerfile**: A multi-stage Dockerfile to build and run the Java application.
- **pom.xml**: The Maven configuration file for building the application.
- **src/**: The source code of the application.
- **target/**: The directory where the packaged `.jar` file will be placed after running Maven.

## Setup Instructions

Follow these steps to build and run the application in a Docker container:

### 1. Clone the Repository

First, clone the project repository to your local machine.

```bash
git clone <repository-url>
cd <repository-directory>
```

### 2. Build the Docker image using the following command:

```bash
docker build -t receipt-processor .
```

This will:

- Use the Dockerfile to build the Java application.
- Pull the necessary base images, install Maven, and package the Java application.

### 3. Run the Docker Container
Once the image is built successfully, you can run it with the following command:

```bash
docker run -p 8080:8080 receipt-processor
```

This command will:

- Run the Docker container.
- Map port 8080 on your host machine to port 8080 inside the container.
- You should now be able to access your application by navigating to http://localhost:8080 in your web browser.

### 4. Stopping the Application
To stop the Docker container, you can use the following command to find the container's ID and stop it:

```bash
docker ps
```
Then, stop the container using its ID:

```bash
docker stop <container-id>
```

Or, you can stop it by name:

```bash
docker stop receipt-processor
```

### Dockerfile Breakdown
Here’s a breakdown of what the Dockerfile does:

Build Stage:

- The Dockerfile starts by using the arm64v8/openjdk:17-jdk-slim image as the base.
- It copies the pom.xml and source code (src/) into the container.
- It then runs Maven to compile the code and create the .jar file.

Run Stage:

- The second part of the Dockerfile uses the openjdk:17-jdk-slim image to create a minimal runtime image.
- The .jar file is copied from the build stage into this image.
- It exposes port 8080 and runs the Java application with the command java -jar /app.jar.