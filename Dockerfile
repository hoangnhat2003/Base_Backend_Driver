FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/backend-driver.jar

RUN mkdir /opt/base-backend-driver

COPY ${JAR_FILE} /opt/base-backend-driver/backend-driver.jar

ENTRYPOINT ["java","-jar","/opt/base-backend-driver/backend-driver.jar"]
