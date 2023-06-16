FROM openjdk:17-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} plurg.jar

ENTRYPOINT ["java", "-jar", "/plurg.jar"]

#EXPOSE 8181