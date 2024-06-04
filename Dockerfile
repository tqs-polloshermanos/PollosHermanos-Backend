FROM openjdk:17-jdk-alpine

VOLUME /tmp
EXPOSE 8080

COPY ./target/tqspolloshermanos-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
