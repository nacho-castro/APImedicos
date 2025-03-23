FROM amazoncorretto:17-alpine-jdk
MAINTAINER NCH
COPY target/api-0.0.1-SNAPSHOT.jar api-medicos.jar
ENTRYPOINT ["java","-jar","/api-medicos.jar"]