# Maven build container 

FROM maven:3.8.8-eclipse-temurin-8 AS maven_build

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package

#pull base image

FROM eclipse-temurin:8-jdk
EXPOSE 8080

COPY --from=maven_build /tmp/target/v1.jar /tmp/v1.jar
ENTRYPOINT ["java","-jar","/tmp/v1.jar"]

