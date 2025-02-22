FROM openjdk:17-jdk-slim
WORKDIR /usr/src/app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
