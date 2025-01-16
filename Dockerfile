
FROM eclipse-temurin:21-jdk-jammy
EXPOSE 8080
ARG JAR_FILE=target/Auction-1.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
