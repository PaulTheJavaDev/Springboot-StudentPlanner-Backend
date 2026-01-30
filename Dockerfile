# Create a new build stage from a base image.
FROM eclipse-temurin:21-jdk AS build

# Change working directory.
WORKDIR /app

# Copy files and directories.
COPY . .

# Execute build commands.
RUN chmod +x mvnw

# Execute build commands.
RUN ./mvnw clean package -DskipTests

# Create a new build stage from a base image.
FROM eclipse-temurin:21-jre

# Change working directory.
WORKDIR /app

# Copy files and directories.
COPY --from=build /app/target/*.jar app.jar

# Describe which ports your application is listening on.
EXPOSE 8080

# Specify default executable.
ENTRYPOINT ["java","-jar","app.jar"]