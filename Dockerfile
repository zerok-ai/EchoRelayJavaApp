FROM --platform=linux/amd64 openjdk:18-alpine
WORKDIR /usr/src/app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 80
CMD ["java", "-jar", "app.jar"]
