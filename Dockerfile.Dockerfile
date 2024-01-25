# Use the official OpenJDK base image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file from the target directory to the container
COPY target/task-1.0.jar /app/app.jar

# Expose the port that the application runs on
EXPOSE 8888

# Specify the command to run on container startup
CMD ["java", "-jar", "app.jar"]
