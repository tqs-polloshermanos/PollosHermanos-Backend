FROM openjdk:17-jdk-alpine

VOLUME /tmp
EXPOSE 8085

COPY ./target/tqspolloshermanos-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
